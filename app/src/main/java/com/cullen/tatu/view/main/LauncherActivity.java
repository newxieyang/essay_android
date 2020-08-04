package com.cullen.tatu.view.main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.cullen.tatu.R;
import com.cullen.tatu.api.ApiAccount;
import com.cullen.tatu.constants.Constants;
import com.cullen.tatu.model.TokenInfo;
import com.cullen.tatu.utils.PermissionsCheckerUtil;
import com.cullen.tatu.utils.http.OkGoUpdateHttpUtil;
import com.cullen.tatu.utils.store.SPSUtils;
import com.cullen.tatu.view.App;
import com.cullen.tatu.view.user.LoginActivity;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import java.util.Optional;

import butterknife.BindView;

public class LauncherActivity extends BaseActivity {

    private final static int SEND_SMS_REQUEST_CODE = 0;


    @BindView(R.id.line)
    LinearLayout txt_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        App.instance.manager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_ACCOUNT_INFO_LOAD));
        App.instance.initOther();
        updateLogic();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.instance.manager.unregisterReceiver(receiver);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initView() {
        checkPermission();
    }



    private void goMain() {

        new Handler().postDelayed(() -> {
            runOnUiThread(() -> {
                Optional<TokenInfo> tokenInfo = SPSUtils.loadTokens();

                Intent intent;

                if (tokenInfo.isPresent()) {
                     intent = new Intent(LauncherActivity.this, HomeActivity
                            .class);
                } else {
                    intent = new Intent(LauncherActivity.this, LoginActivity
                            .class);
                }

                startActivity(intent);
                finish();


            });
        }, 1500);


    }

    /**
     * 权限检查
     */
    public void checkPermission() {
        // 需要的读写权限
        String[] mPermissionList = new String[]{
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        PermissionsCheckerUtil mChecker = new PermissionsCheckerUtil(this);
        if (mChecker.lacksPermissions(mPermissionList)) {
            this.requestPermissions(mPermissionList, SEND_SMS_REQUEST_CODE);
        }

    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ACTION_ACCOUNT_INFO_LOAD.equals(intent.getAction())) {
                goMain();
            }
        }
    };


    /**
     * 检查更新
     */
    public void updateLogic() {

        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(ApiAccount.path(ApiAccount.Api.update))
                .handleException(e -> ApiAccount.initInfo())
                .setUpdateDialogFragmentListener(updateApp -> ApiAccount.initInfo())
                //实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                .build()
                .checkNewApp(new UpdateCallback() {

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    @Override
                    protected void noNewApp(String error) {
                        super.noNewApp(error);
                        ApiAccount.initInfo();
                    }


                });


    }
}
