package com.tatu.essay.utils.db;

import android.content.Context;
import android.util.Log;

import com.tatu.essay.model.gen.DaoMaster;

import org.greenrobot.greendao.database.Database;

import static com.tatu.essay.model.gen.DaoMaster.dropAllTables;


/**
 * Created by xieyang on 2017/6/14.
 */

public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {
    public GreenDaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d("", "upgrade Db : old version" + oldVersion + ", new version" + newVersion);
        dropAllTables(db, true);
        onCreate(db);
    }
}
