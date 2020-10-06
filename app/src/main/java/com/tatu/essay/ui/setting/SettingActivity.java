package com.tatu.essay.ui.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.tatu.essay.R;
import com.tatu.essay.api.AccountApi;
import com.tatu.essay.model.UserModel;
import com.tatu.essay.ui.main.BaseActivity;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;
import com.tatu.essay.utils.store.SPSUtils;

import butterknife.BindView;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.avatar)
    BezelImageView avatar;

    @BindView(R.id.name)
    EditText nameEditor;

    @BindView(R.id.phone)
    TextView phoneView;

    @BindView(R.id.des)
    EditText desEditor;

    @BindView(R.id.btn_save)
    Button btnSave;

    UserModel info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int initLayout() {
        return R.layout.activity_settinng;
    }

    @Override
    protected void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.setting_title);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(view -> finish());

        info = SPSUtils.loadUser();

        phoneView.setText(info.getPhone());

        if (!TextUtils.isEmpty(info.getNickName())) {
            nameEditor.setText(info.getNickName());
        }

        if (!TextUtils.isEmpty(info.getDes())) {
            desEditor.setText(info.getDes());
        }

        btnSave.setOnClickListener(view -> save());
    }


    private void save() {
        dismissKeyboard();
        String nickname = "";
        if (nameEditor.getText().length() > 0) {
            nickname = nameEditor.getText().toString();
        }
        String des = "";
        if (desEditor.getText().length() > 0) {
            des = desEditor.getText().toString();
        }


        AccountApi.updateUserInfo(info.getId(), nickname, des, new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                if (response.code == 200) {
                    showConfirm("保存成功");
                    AccountApi.initInfo();
                } else {
                    showErrorMsg("保存失败");
                }

            }
        });
    }
}