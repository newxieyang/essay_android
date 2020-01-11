package com.cullen.tatu.view;

import android.app.Application;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cullen.tatu.api.Api;
import com.cullen.tatu.api.ApiAccount;
import com.cullen.tatu.view.main.BaseActivity;
import com.cullen.tatu.model.gen.DaoSession;
import com.cullen.tatu.model.gen.EssayModelDao;
import com.cullen.tatu.model.gen.FleetModelDao;
import com.cullen.tatu.utils.db.DbCore;
import com.cullen.tatu.utils.http.HttpUtils;
import com.cullen.tatu.utils.store.SPSUtils;
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
                    HttpUtils.initOKHTTP(tokenInfo.getToken());
                    ApiAccount.initInfo();
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
        activities.clear();

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
