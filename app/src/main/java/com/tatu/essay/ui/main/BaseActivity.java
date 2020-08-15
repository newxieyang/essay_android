package com.tatu.essay.ui.main;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.snackbar.Snackbar;
import com.tamsiree.rxkit.RxKeyboardTool;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.ui.App;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance.appendActivity(this);
        setContentView(initLayout());
        ButterKnife.bind(this);
        initView();

        StatusBarCompat.setStatusBarColor(this, ResourceConstants.colorWhite);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.instance.removeActivity(this);
    }


    protected void showErrorMsg(String msg) {
        Snackbar.make(getRootView(), msg, Snackbar.LENGTH_LONG).setBackgroundTint(Color.argb(200,213,0,0)).show();
    }


    protected void showConfirm(String msg) {
        Snackbar.make(getRootView(), msg, Snackbar.LENGTH_LONG).setBackgroundTint(Color.argb(200,255,215,0)).show();
    }



    public View getRootView() {
        return getWindow().getDecorView();
    }


    protected abstract int initLayout();

    protected abstract void initView();




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onBackPressed() {
        if(isSoftShowing()) {
            dismissKeyboard();
        } else {
            super.onBackPressed();
        }
    }


    protected void dismissKeyboard() {
        RxKeyboardTool.hideSoftInput(this);
    }

    @Override
    public void finish() {
        if(isSoftShowing()) {
            RxKeyboardTool.hideSoftInput(this);
        }
        super.finish();
    }


    /**
     *判断输入法是否显示
     * @return
     */
    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight * 2 / 3 > rect.bottom;
    }
}
