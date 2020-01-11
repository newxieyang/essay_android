package com.cullen.tatu.utils.db;

import android.content.Context;

import com.cullen.tatu.model.gen.DaoMaster;
import com.cullen.tatu.model.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;


/**
 * Created by xieyang on 2017/6/14.
 */

public class DbCore {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static Context mContext;
    private static String DB_NAME;


    public static void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mContext = context.getApplicationContext();
        DB_NAME = dbName + ".db";
    }

    public static void reset() {
        daoMaster = null;
        daoSession = null;
    }


    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {

            //此处不可用 DaoMaster.DevOpenHelper, 那是开发辅助类，我们要自定义一个，方便升级
            DaoMaster.OpenHelper helper = new GreenDaoOpenHelper(mContext, DB_NAME);
            daoMaster = new DaoMaster(helper.getWritableDb());
            enableQueryBuilderLog();
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static void enableQueryBuilderLog() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
