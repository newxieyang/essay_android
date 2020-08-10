package com.tatu.essay.ui.essay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.tatu.essay.R;
import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumDataState;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.ui.main.BaseActivity;
import com.tatu.essay.utils.GsonUtils;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class EssayDetailActivity extends BaseActivity {

    @BindView(R.id.contentView)
    AppCompatEditText contentView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private boolean draft = false;

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

        draft = data.getState().equals(EnumDataState.DRAFT.getState());
        contentView.setEnabled(draft);

        String content = data.getContent();
        contentView.setText(content);

        int iconResId = draft?R.drawable.ic_baseline_done_24:R.drawable.ic_baseline_arrow_back_ios_24;
        toolbar.setNavigationIcon(iconResId);

        toolbar.setNavigationOnClickListener((View view)-> {
            Editable editable = contentView.getText();

            // 标题是空， 内容也是空， 等同删除
            if(editable == null && TextUtils.isEmpty(data.getTitle())) {
                save(data.getId(), EnumDataState.DELETE, "");
                finish();
                return;
            }

            // 草稿已经改变
            if(!contentView.getText().toString().trim().equals(content.trim())) {
                save(data.getId(), EnumDataState.DRAFT, editable.toString());
            }

            finish();

        });
        toolbar.inflateMenu(R.menu.menu_essay_favorite);
        toolbar.setOnMenuItemClickListener(item -> {
            Editable editable = contentView.getText();
            save(data.getId(), EnumDataState.NORMAL, editable.toString());
            return false;
        });

        toolbar.setTitle(data.getTitle());



    }



    private void save(Long id, EnumDataState enumDataState, String content) {

        Map<String, Object> map = new HashMap<>();
        map.put("state", enumDataState.getState());
        map.put("content", content);

        EssayApi.update(id, GsonUtils.toJson(map), new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if(response.code != 200) {
                    if(!EnumDataState.DRAFT.equals(enumDataState)) {
                        Snackbar.make(getRootView(), "保存失败", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.finish();
    }
}
