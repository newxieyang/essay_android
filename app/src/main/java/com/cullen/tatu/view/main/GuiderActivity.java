package com.cullen.tatu.view.main;

import android.os.Bundle;

import com.cullen.tatu.R;
import com.cullen.tatu.logic.NavigationStyle;

public class GuiderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_guider;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected String initTitleText() {
        return null;
    }

    @Override
    protected NavigationStyle navigationStyle() {
        return null;
    }
}
