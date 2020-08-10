package com.tatu.essay.ui.main;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tatu.essay.R;
import com.tatu.essay.api.ApiAccount;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.model.TokenInfo;
import com.tatu.essay.utils.PermissionsCheckerUtil;
import com.tatu.essay.utils.http.OkGoUpdateHttpUtil;
import com.tatu.essay.utils.store.SPSUtils;
import com.tatu.essay.ui.App;
import com.tatu.essay.ui.user.LoginActivity;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;

import java.util.Optional;

public class LauncherActivity extends BaseActivity {

    private final static int SEND_SMS_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        App.instance.manager.registerReceiver(receiver, new IntentFilter(EnumAction.AccountLoad.getAction()));
        App.instance.initOther();
//        updateLogic();

        goMain();

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
                Log.e("goto main handle", "goto main");
                if (tokenInfo.isPresent() && tokenInfo.get().getToken() != null) {
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
            if (EnumAction.AccountLoad.getAction().equals(intent.getAction())) {
                Log.e("goto main broadcast", "goto main");
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
                .handleException(e -> {ApiAccount.initInfo(); Log.e("更新地址失败","更新出错了。。。");})
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
                        Log.e("no app","更新出错了。。。" + error);
                        ApiAccount.initInfo();
                    }


                });


    }
}
