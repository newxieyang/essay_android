
package com.tatu.essay.api;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.model.UserModel;
import com.tatu.essay.ui.App;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;
import com.tatu.essay.utils.store.SPSUtils;

public class AccountApi extends Api {


    public static final String url_auth = base_url + "login";

    public static final String url_user_info = base_url + "user/info";

    public static final String url_android = base_url + "android";

    public static final String url_register = base_url + "user/register";


    public static void initInfo() {
        Log.e("initInfo", "这是第几次调用");
        HttpParams params = new HttpParams();
        OkGo.<String>get(url_user_info).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                // 如果是 200 就保存用户信息  否则就清除token信息
                // TODO 未来要去刷新TOKEN 信息
                if (response.code == 200) {
                    UserModel user = new Gson().fromJson(response.data.toString(), UserModel.class);
                    authorId = user.getId();
                    SPSUtils.saveUser(user);
                } else {
                    SPSUtils.saveToken(null);
                }
                App.instance.manager.sendBroadcast(new Intent(EnumAction.AccountLoad.getAction()));
            }
        });
    }


    public static void auth(String username, String password, JsonCallback callback) {
        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("password", password);
//        params.put("grant_type", "password");
//        params.put("client_id", "MemberSystem");
        OkGo.<String>post(url_auth).params(params).execute(callback);
    }


    public static void register(String phone, String password, JsonCallback callback) {
        HttpParams params = new HttpParams();
        params.put("password", password);
        params.put("phone", phone);
        OkGo.<String>post(url_register).params(params).execute(callback);
    }


    public static void updateUserInfo(Long id, String nickname, String des, JsonCallback callback) {
        HttpParams params = new HttpParams();
        params.put("nickname", nickname);
        params.put("des", des);
        params.put("id", id);
        OkGo.<String>put(url_user_info).params(params).execute(callback);
    }

}
