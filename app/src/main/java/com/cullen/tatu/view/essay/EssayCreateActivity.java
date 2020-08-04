package com.cullen.tatu.view.essay;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.cullen.tatu.R;
import com.cullen.tatu.api.ApiEssay;
import com.cullen.tatu.utils.http.JsonCallback;
import com.cullen.tatu.utils.http.ResponseApi;
import com.cullen.tatu.view.main.BaseActivity;

import butterknife.BindView;

public class EssayCreateActivity extends BaseActivity {


    @BindView(R.id.contentView)
    AppCompatEditText contentView;

    @BindView(R.id.activity_header)
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
        toolbar.setNavigationOnClickListener((View view)->finish());
        toolbar.inflateMenu(R.menu.bar_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            saveEssay();
            return false;
        });
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
