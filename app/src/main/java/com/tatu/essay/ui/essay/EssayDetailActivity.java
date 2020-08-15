package com.tatu.essay.ui.essay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.lzy.okgo.model.HttpParams;
import com.tatu.essay.R;
import com.tatu.essay.api.Api;
import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.logic.EnumDataState;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.model.FavoriteModel;
import com.tatu.essay.service.FavoritesService;
import com.tatu.essay.ui.main.BaseActivity;
import com.tatu.essay.utils.GsonUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class EssayDetailActivity extends BaseActivity {

    @BindView(R.id.contentView)
    AppCompatEditText contentView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private int state = 99;

    private FavoriteModel favoriteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_essay_detail;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        //接收从First_Activity中传输的数据
        EssayModel data = (EssayModel) intent.getSerializableExtra("essay");

        boolean draft = data.getState().equals(EnumDataState.DRAFT.getState());
        contentView.setEnabled(draft);

        String content = data.getContent();
        contentView.setText(content);


        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);

        toolbar.setNavigationOnClickListener((View view) -> {
            Editable editable = contentView.getText();

            // 标题是空， 内容也是空， 等同删除
            if (editable == null && TextUtils.isEmpty(data.getTitle())) {
                save(data.getId(), EnumDataState.DELETE, "");
                finish();
                return;
            }

            // 草稿已经改变
            if (!contentView.getText().toString().trim().equals(content.trim())) {
                save(data.getId(), EnumDataState.DRAFT, editable.toString());
            }

            finish();

        });

        toolbar.inflateMenu(R.menu.menu_essay_favorite);

        int iconResId;
        if (draft) {
            iconResId = R.drawable.ic_baseline_done_24;
            state = 0;
        } else {
            favoriteModel = FavoritesService.findByEssayId(data.getId());
            if (favoriteModel != null) {
                iconResId = R.drawable.ic_baseline_favorite_24;
                state = 1;
            } else {
                iconResId = R.drawable.ic_baseline_favorite_border_24;
                state = 2;
            }
        }


        toolbar.getMenu().getItem(0).setIcon(iconResId);
        toolbar.setOnMenuItemClickListener(item -> {
            if (state == 0) {
                Editable editable = contentView.getText();
                save(data.getId(), EnumDataState.NORMAL, editable.toString());
            }
            if (state == 1) {
                favorite(data.getId());
            }

            if (state == 2) {
                favoriteDelete();
            }

            return true;
        });

        toolbar.setTitle(data.getTitle());


    }


    /***
     * 保存
     * @param id
     * @param enumDataState
     * @param content
     */
    private void save(Long id, EnumDataState enumDataState, String content) {

        HttpParams map = new HttpParams();
        map.put("state", enumDataState.getState());
        map.put("content", content);

        EssayApi.update(id, map);
    }


    /***
     * 收藏
     * @param essayId
     */
    private void favorite(long essayId) {
        Map<String, Object> map = new HashMap<>();
        map.put("essayId", essayId);
        map.put("userId", Api.authorId);

        EssayApi.saveFavorite(GsonUtils.toJson(map));
    }


    /***
     * 取消收藏
     */
    private void favoriteDelete() {

        FavoritesService.deleteFavorite(favoriteModel);

        EssayApi.deleteFavorite(favoriteModel.getId());
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (EnumAction.EssayDetail.getAction().equals(intent.getAction())) {
                switch (state) {
                    case 0:
                        Snackbar.make(getRootView(), "保存失败", Snackbar.LENGTH_LONG).show();
                        break;
                    case 1:
                        Snackbar.make(getRootView(), "收藏失败", Snackbar.LENGTH_LONG).show();
                        break;
                    case 2:
                        Snackbar.make(getRootView(), "取消收藏失败", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();
    }
}
