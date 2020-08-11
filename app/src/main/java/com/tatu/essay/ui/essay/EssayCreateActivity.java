package com.tatu.essay.ui.essay;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.tatu.essay.R;
import com.tatu.essay.api.Api;
import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumDataState;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.ui.main.BaseActivity;
import com.tatu.essay.utils.GsonUtils;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;

import butterknife.BindView;

public class EssayCreateActivity extends BaseActivity {



    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_essay_create;
    }

    @Override
    protected void initView() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener((View view) -> {
            saveDraft();
        });
        toolbar.inflateMenu(R.menu.menu_essay_create);
        toolbar.setOnMenuItemClickListener(item -> {
            saveEssay();
            return false;
        });
    }


    /**
     * 组装参数
     * @return
     */
    private EssayModel buildParams() {

        Editable titleEditable = ((AppCompatEditText) findViewById(R.id.title)).getText();
        Editable contentEditable = ((AppCompatEditText) findViewById(R.id.contentView)).getText();

        if(titleEditable == null && contentEditable == null) {
            return null;
        }

        EssayModel essayModel = new EssayModel();

        if(titleEditable != null) {
            essayModel.setTitle(titleEditable.toString());
        }

        if(contentEditable != null) {
            essayModel.setContent(contentEditable.toString());
        }

        essayModel.setCreateBy(Api.authorId);
        return essayModel;

    }

    /***
     * 保存到草稿
     */
    private void saveDraft() {

        EssayModel essayModel = buildParams();
        if(essayModel == null) {
            finish();
            return;
        }

        essayModel.setState(EnumDataState.DRAFT.getState());

        EssayApi.save(GsonUtils.toJson(essayModel), new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if(response.code != 200) {
                    // TODO 保存到本地
                }
                finish();
            }
        });
    }


    /***
     * 保存
     */
    private void saveEssay() {

        EssayModel essayModel = buildParams();
        if(essayModel == null) {
            Snackbar.make(getRootView(), "请输入内容", Snackbar.LENGTH_LONG).show();
            return;
        }

        essayModel.setState(EnumDataState.NORMAL.getState());

        EssayApi.save(GsonUtils.toJson(essayModel), new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if(response.code == 200) {
                    Snackbar.make(getRootView(), "保存成功", Snackbar.LENGTH_LONG).show();
                    finish();
                } else {
                    Snackbar.make(getRootView(), "保存失败", Snackbar.LENGTH_LONG).show();
                }
            }
        });



    }
}
