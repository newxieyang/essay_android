package com.cullen.tatu.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;

import com.cullen.tatu.model.UserModel;
import com.cullen.tatu.view.App;
import com.cullen.tatu.view.main.LauncherActivity;
import com.cullen.tatu.utils.store.SPSUtils;
import com.cullen.tatu.view.user.LoginActivity;
import com.lzy.okgo.OkGo;

public class ApplicationUtils {







    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad() {
        return (App.instance.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }





    public static boolean isApkDebugAble(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }


    /***
     * 退出
     */
    public static void exitApp() {

        // 断开http请求
        OkGo.getInstance().cancelAll();

     /*   DataService.getDataService().clearSession();
        DbCore.reset();*/

        // 通知token改变了
//        BoardNotification.userTokenUpdateNotification(false);

        // 清理用户信息
        removeAccountInfo();



    }



    /***
     * 进入登陆页面
     * @param App
     */
    private static void toLoginActivity(App App) {
        // finish其他activity
        App.removeAll();
        // 判断是否是在前台,如果在前台,跳转到登录界面
        Intent intent = new Intent(App, LauncherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.startActivity(intent);

    }


    /***
     * 清理用户信息
     */
    public static void removeAccountInfo() {
        // 清理用户信息
        SPSUtils.saveUser(new UserModel());
        SPSUtils.deleteToken();
        toLoginActivity(App.instance);

    }



}
