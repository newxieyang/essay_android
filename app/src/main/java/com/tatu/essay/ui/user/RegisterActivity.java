package com.tatu.essay.ui.user;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.tamsiree.rxkit.RxAnimationTool;
import com.tamsiree.rxkit.RxBarTool;
import com.tamsiree.rxkit.RxRegTool;
import com.tamsiree.rxui.activity.AndroidBug5497Workaround;
import com.tamsiree.rxui.view.loadingview.style.Circle;
import com.tatu.essay.R;
import com.tatu.essay.api.ApiAccount;
import com.tatu.essay.utils.CustomTextWatcher;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;
import com.tatu.essay.ui.main.BaseActivity;


import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.iv_clean_phone)
    ImageView mIvCleanPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.clean_password)
    ImageView mCleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView mIvShowPwd;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.service)
    TextView mService;


    private Circle mCircleDrawable;

    private int keyHeight = 0; //软件盘弹起后所占高度

    private String phoneNum;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        RxBarTool.setTransparentStatusBar(this);//状态栏透明化
        RxBarTool.StatusBarLightMode(this);

        if (isFullScreen(this)) {
            AndroidBug5497Workaround.Companion.assistActivity(this);
        }

        int screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3

        initEvent();
    }

    private void initEvent() {
        mEtMobile.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mIvCleanPhone.getVisibility() == View.GONE) {
                    mIvCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mIvCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        mEtPassword.addTextChangedListener(new CustomTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && mCleanPassword.getVisibility() == View.GONE) {
                    mCleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    mCleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty()) {
                    return;
                }
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(RegisterActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    mEtPassword.setSelection(s.length());
                }
            }
        });

        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener((View v, MotionEvent event) ->
                true
        );
        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("----", "up------>" + (oldBottom - bottom));
                    int dist = mContent.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        RxAnimationTool.zoomIn(mLogo, 0.6f, dist);
                    }
                    mService.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("----", "down------>" + (bottom - oldBottom));
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        RxAnimationTool.zoomOut(mLogo, 0.6f);
                    }
                    mService.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    public boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    @OnClick({R.id.iv_clean_phone, R.id.clean_password, R.id.iv_show_pwd,
            R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean_phone:
                mEtMobile.setText("");
                break;
            case R.id.clean_password:
                mEtPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (mEtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIvShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = mEtPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    mEtPassword.setSelection(pwd.length());
                break;
            case R.id.btn_register:
                doRegister();
                break;

        }
    }

    private void doRegister() {

        dismissKeyboard();

        phoneNum = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            showErrorMsg(getString(R.string.login_error_username_empty));
            return;
        }

        if (!RxRegTool.isMobileExact(phoneNum)) {
            showErrorMsg(getString(R.string.login_error_username_empty));
            return;
        }

        password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showErrorMsg(getString(R.string.login_error_password_empty));
            return;
        }

        register();
    }


    private void register() {

        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(Color.WHITE);
        mBtnRegister.setCompoundDrawables(null, null, mCircleDrawable, null);
        mCircleDrawable.setVisible(true, true);
        mCircleDrawable.start();

        ApiAccount.register(phoneNum, password, new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
//                iLoginProgress.setVisibility(View.GONE);
                mCircleDrawable.stop();
                mCircleDrawable.setVisible(false, true);
                if (response.code == 200) {
                    showConfirm("恭喜您， 注册成功");
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }, 2000);
                } else if (response.code == 500) {
                    showErrorMsg("该手机号码已注册！！！请使用另外的手机号码");
                } else {
                    showErrorMsg(response.errMsg);
                }
            }
        });

    }
}
