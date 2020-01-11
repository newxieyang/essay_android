package com.cullen.tatu.view.essay;


import android.os.Bundle;
import android.text.TextUtils;

import com.cullen.tatu.R;
import com.cullen.tatu.api.ApiEssay;
import com.cullen.tatu.logic.NavigationStyle;
import com.cullen.tatu.view.main.BaseActivity;
import com.cullen.tatu.utils.http.JsonCallback;
import com.cullen.tatu.utils.http.ResponseApi;
import com.cullen.tatu.view.ui.LineEditText;

import butterknife.BindView;

public class EssayCreateActivity extends BaseActivity {


    @BindView(R.id.contentView)
    LineEditText contentView;

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
        findViewById(R.id.close).setOnClickListener(view -> finish());
        findViewById(R.id.save).setOnClickListener(view -> saveEssay());
    }

    @Override
    protected String initTitleText() {
        return "写下我的喜怒哀乐";
    }

    @Override
    protected NavigationStyle navigationStyle() {
        return null;
    }



    private void saveEssay() {
        String content = contentView.getText().toString();

        if(TextUtils.isEmpty(content)) {
            showErrorMsg("您还没有写下您的喜怒哀乐呢！");
            return;
        }


        ApiEssay.save( content, new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if(response.code == 200) {
                    showInfo("保存成功 ^_^");
                    finish();
                } else {
                    showErrorMsg("保存失败 LOL");
                }
            }
        });

    }
}
