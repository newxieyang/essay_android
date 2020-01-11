package com.cullen.tatu.view.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;
import com.cullen.tatu.constants.ResourceConstants;
import com.cullen.tatu.logic.NavigationStyle;
import com.cullen.tatu.utils.IconResources;
import com.githang.statusbar.StatusBarCompat;
import com.vondear.rxtool.RxKeyboardTool;
import com.vondear.rxtool.view.RxToast;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    protected NavigationStyle style = NavigationStyle.Normal;
    protected Toolbar iHeaderView;

    @Nullable
    @BindView(R.id.iRightButton)
    public ImageButton iRightButton;

    @Nullable
    @BindView(R.id.iRightButton2)
    public ImageButton iRightButton2;

    @Nullable
    @BindView(R.id.iRightButton3)
    public ImageButton iRightButton3;

    @Nullable
    @BindView(R.id.iTitleText)
    protected TextView iTitleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance.appendActivity(this);
        setContentView(initLayout());
        ButterKnife.bind(this);
        initView();

        StatusBarCompat.setStatusBarColor(this, ResourceConstants.colorNav);

    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    protected void init() {
        View header = findViewById(R.id.activity_header);
        if (header != null) {
            iHeaderView = (Toolbar) header;
            setSupportActionBar(iHeaderView);
            String title = initTitleText();
            if (title != null) {
                setTitle(title);
                if(iTitleText!=null) {
                    iTitleText.setText(title);
                }
            }
            NavigationStyle style = navigationStyle();
            ActionBar bar = getSupportActionBar();
            if (bar == null) {
                return;
            }
            bar.setDisplayHomeAsUpEnabled(false);

            if (style != null) {
                this.style = style;
                switch (style) {
                    case SliderMenu:
                        Drawable sliderIcon = IconResources.getNavigationMenuIcon();
                        iHeaderView.setNavigationIcon(sliderIcon);
                        break;
                    case Back:
                        bar.setDisplayHomeAsUpEnabled(true);
                        iHeaderView.setNavigationIcon(IconResources.getNavigationBackIcon());
                        iHeaderView.setNavigationOnClickListener((View v) ->
                                finish()
                        );
                        break;
                    case Dismiss:
                        bar.setDisplayHomeAsUpEnabled(true);
                        iHeaderView.setNavigationIcon(IconResources.getCloseIcon());
                        iHeaderView.setNavigationOnClickListener((View v) ->
                            finish()
                        );
                        break;

                    case Hide:
                        bar.hide();
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.instance.removeActivity(this);
    }


    protected void showErrorMsg(String msg) {
        RxToast.error(this, msg, Toast.LENGTH_SHORT, true).show();
    }


    protected void showConfirm(String msg) {
        RxToast.warning(this, msg, Toast.LENGTH_SHORT, true).show();
    }

    protected void showInfo(String msg) {
        RxToast.success(this, msg, Toast.LENGTH_SHORT, true).show();
    }


    public View getRootView() {
        return getWindow().getDecorView();
    }


    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract String initTitleText();

    protected abstract NavigationStyle navigationStyle();


    @Override
    public void onClick(View v) {
    }


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
        dismissKeyboard();
    }


    protected void dismissKeyboard() {
        RxKeyboardTool.hideSoftInput(this);
    }

    @Override
    public void finish() {
        dismissKeyboard();
        super.finish();
    }
}
