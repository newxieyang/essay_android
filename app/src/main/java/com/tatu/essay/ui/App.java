package com.tatu.essay.ui;

import android.app.Application;
import android.text.TextUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.tatu.essay.api.Api;
import com.tatu.essay.model.gen.DaoSession;
import com.tatu.essay.model.gen.EssayModelDao;
import com.tatu.essay.model.gen.FleetModelDao;
import com.tatu.essay.utils.db.DbCore;
import com.tatu.essay.utils.http.HttpUtils;
import com.tatu.essay.utils.store.SPSUtils;
import com.tatu.essay.ui.main.BaseActivity;
import com.vondear.rxtool.RxEncryptTool;
import com.vondear.rxtool.RxTool;

import java.util.Stack;

public class App extends Application {

    public static App instance;

    private DaoSession daoSession = null;

    public Stack<BaseActivity> activities = new Stack<>();


   public LocalBroadcastManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RxTool.init(this);
        manager = LocalBroadcastManager.getInstance(this);
    }



    public void initOther() {

        SPSUtils.loadTokens().ifPresent((tokenInfo) -> {
                    Api.phone = tokenInfo.getUsername();
                    if(TextUtils.isEmpty(Api.phone)) {
                        return;
                    }
                    HttpUtils.initOKHTTP(tokenInfo.getToken());
//                    ApiAccount.initInfo();
                    initDataBase();
                });

    }


    /***
     * 初始化数据库
     */
    public void initDataBase() {

        String dbName = RxEncryptTool.encryptMD5ToString(Api.phone);

        if (daoSession != null) {
            daoSession.clear();
        }

        DbCore.init(this, dbName);
        daoSession = DbCore.getDaoSession();

    }


    private DaoSession getDaoSession() {
        if (daoSession == null) {
            initDataBase();
        }
        return daoSession;
    }


    public void appendActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }


    /**
     * 结束所有Activity
     */
    public void removeAll() {

        while (!activities.isEmpty()) {
            activities.pop().finish();
        }

    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        removeAll();

        System.exit(0);
    }


    public EssayModelDao getEssayDao() {
        return getDaoSession().getEssayModelDao();
    }


    public FleetModelDao getFleetDao() {
        return getDaoSession().getFleetModelDao();
    }
}
