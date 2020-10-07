package com.tatu.essay.utils.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tatu.essay.vo.UserVo;
import com.tatu.essay.ui.App;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by essay on 01/02/2018.
 */

public class SPSUtils {


    // 统一SP文件存储名
    private static final String SP_FILE_NAME = "SP_TATU_FILE";

    private static SharedPreferences.Editor editor() {
        return sp().edit();
    }


    private static SharedPreferences sp() {
        return App.instance.getSharedPreferences(SPSUtils.SP_FILE_NAME, Context.MODE_PRIVATE);
    }


    /**
     * 保存
     *
     * @param object
     */
    private static void saveObject(String spKey,Object object) {
        SharedPreferences.Editor editor = SPSUtils.editor();
        if(Objects.isNull(object)) {
            deleteObject(spKey);
        } else {

            String json = new Gson().toJson(object);
            editor.putString(SPSUtils.SP_KEY_USER, json);
            editor.commit();
        }
    }


    /***
     * 获取值
     * @return
     */
    private static String loadObject(String spKey) {
        SharedPreferences sp = SPSUtils.sp();
        return sp.getString(spKey, "");
    }


    private static void deleteObject(String spKey) {
        SharedPreferences sp = SPSUtils.sp();
        if(sp.contains(spKey)) {
            SharedPreferences.Editor editor = SPSUtils.editor();
            editor.remove(spKey);
            editor.apply();
        }
    }


    /****
     * user ........
     */

    private static final String SP_KEY_USER = "KEY_USER";


    public static void saveUser(UserVo user) {
        SPSUtils.saveObject(SP_KEY_USER, user);
    }

    public static void deleteUser() {
        SPSUtils.deleteObject(SP_KEY_USER);
    }

    public static Optional<UserVo> loadUser() {
        String json = SPSUtils.loadObject(SP_KEY_USER);
        if (!TextUtils.isEmpty(json)) {
            return Optional.of(new Gson().fromJson(json, UserVo.class));
        }
        return Optional.empty();
    }





    /*****
     * launcher.......
     */
    private static final String SP_KEY_LAUNCHER = "KEY_LAUNCHER";

    public static void saveNew(boolean hasRead) {

        SharedPreferences.Editor editor = SPSUtils.editor();
        editor.putBoolean(SP_KEY_LAUNCHER, hasRead);
        editor.apply();
    }


    public static boolean loadNew() {
        SharedPreferences sp = SPSUtils.sp();
        return sp.getBoolean(SP_KEY_LAUNCHER, false);
    }

}
