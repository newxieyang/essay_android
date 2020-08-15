package com.tatu.essay.utils.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tatu.essay.model.TokenInfo;
import com.tatu.essay.model.UserModel;
import com.tatu.essay.ui.App;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by essay on 01/02/2018.
 */

public class SPSUtils {


    // 统一SP文件存储名
    private static final String SP_FILE_NAME = "SP_TATU_FILE";

    private static SharedPreferences.Editor editor(String spFileName) {
        return sp(spFileName).edit();
    }


    private static SharedPreferences sp(String spFileName) {
        return App.instance.getSharedPreferences(spFileName, Context.MODE_PRIVATE);
    }


    /**
     * 保存
     *
     * @param SP_KEY
     * @param object
     */
    private static void saveObject(String SP_KEY, Object object) {
        SharedPreferences.Editor editor = SPSUtils.editor(SP_FILE_NAME);
        String json = Objects.isNull(object)?"":new Gson().toJson(object);
        editor.putString(SP_KEY, json);
        editor.commit();
    }


    /***
     * 获取值
     * @param spKey
     * @return
     */
    private static String loadObject(String spKey) {
        SharedPreferences sp = SPSUtils.sp(SP_FILE_NAME);
        return sp.getString(spKey, "");
    }


    /****
     * user ........
     */

    private static final String SP_KEY_USER = "KEY_USER";


    public static void saveUser(UserModel user) {
        SPSUtils.saveObject(SP_KEY_USER, user);
    }

    public static void deleteUser() {
        SPSUtils.saveObject(SP_KEY_USER, new UserModel());
    }

    public static UserModel loadUser() {
        String json = SPSUtils.loadObject(SP_KEY_USER);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, UserModel.class);
        }
        return new UserModel();
    }


    /******
     * token ........
     */

    private static final String SP_KEY_TOKEN = "KEY_TOKEN";


    public static void saveToken(TokenInfo account) {
        SPSUtils.saveObject(SP_KEY_TOKEN, account);
    }



    public static Optional<TokenInfo> loadTokens() {
        String json = SPSUtils.loadObject(SP_KEY_TOKEN);

        TokenInfo tokenInfo = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                tokenInfo = new Gson().fromJson(json, TokenInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(tokenInfo);
    }


    public static void deleteToken() {
        SPSUtils.saveToken(new TokenInfo());
    }


    /*****
     * launcher.......
     */
    private static final String SP_KEY_LAUNCHER = "KEY_LAUNCHER";

    public static void saveNew(boolean hasRead) {

        SharedPreferences.Editor editor = SPSUtils.editor(SP_FILE_NAME);
        editor.putBoolean(SP_KEY_LAUNCHER, hasRead);
        editor.commit();
    }


    public static boolean loadNew() {
        SharedPreferences sp = SPSUtils.sp(SP_FILE_NAME);
        return sp.getBoolean(SP_KEY_LAUNCHER, false);
    }

}
