package com.cullen.tatu.view.user;

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
import android.view.KeyEvent;
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

import com.cullen.tatu.R;
import com.cullen.tatu.api.ApiAccount;
import com.cullen.tatu.model.TokenInfo;
import com.cullen.tatu.model.UserModel;
import com.cullen.tatu.utils.CustomTextWatcher;
import com.cullen.tatu.utils.http.JsonCallback;
import com.cullen.tatu.utils.http.ResponseApi;
import com.cullen.tatu.utils.store.SPSUtils;
import com.cullen.tatu.view.App;
import com.cullen.tatu.view.main.BaseActivity;
import com.cullen.tatu.view.main.MainActivity;
import com.vondear.rxtool.RxAnimationTool;
import com.vondear.rxtool.RxBarTool;
import com.vondear.rxui.activity.AndroidBug5497Workaround;
import com.vondear.rxui.view.progressing.style.Circle;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


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
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.service)
    TextView mService;


    private Circle mCircleDrawable;

    private int keyHeight = 0; //软件盘弹起后所占高度

    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        RxBarTool.setTransparentStatusBar(this);//状态栏透明化
        RxBarTool.StatusBarLightMode(this);

        if (isFullScreen(this)) {
            AndroidBug5497Workaround.assistActivity(this);
        }

        int screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3

        UserModel records = SPSUtils.loadUser();

        username = records.getPhone();

        if (!TextUtils.isEmpty(username)) {
            mEtMobile.setText(username);
        }
        if (!TextUtils.isEmpty(password)) {
            mEtPassword.setText(password);
        }

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
                    Toast.makeText(LoginActivity.this, "请输入数字或字母", Toast.LENGTH_SHORT).show();
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
            R.id.btn_login, R.id.register})
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
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.btn_login:
                doLogin();
                break;
        }
    }

    private void doLogin() {

        dismissKeyboard();

        username = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(username)) {
            showErrorMsg(getString(R.string.login_error_username_empty));
            return;
        }

        password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            showErrorMsg(getString(R.string.login_error_password_empty));
            return;
        }

        login();
    }


    private void login() {

        mCircleDrawable = new Circle();
        mCircleDrawable.setBounds(0, 0, 100, 100);
        mCircleDrawable.setColor(Color.WHITE);
        mBtnLogin.setCompoundDrawables(null, null, mCircleDrawable, null);
        mCircleDrawable.setVisible(true, true);
        mCircleDrawable.start();

        ApiAccount.auth(username, password, new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
//                iLoginProgress.setVisibility(View.GONE);
                mCircleDrawable.stop();
                mCircleDrawable.setVisible(false, true);
                if (response.code == 200) {
                    // 保存用户

                    TokenInfo tokenEntity = new TokenInfo(response.data.get("authorization").getAsString());
                    tokenEntity.setUsername(username);
                    SPSUtils.saveToken(tokenEntity);

                    new Handler().postDelayed(() -> {
                        // 初始化邮件
                        App.instance.initOther();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }, 1000);
                } else {
                    showErrorMsg(response.errMsg);
                }
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //不执行父类点击事件
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }
}
