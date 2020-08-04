
package com.cullen.tatu.api;

import android.content.Intent;
import android.util.Log;

import com.cullen.tatu.view.App;
import com.cullen.tatu.constants.Constants;
import com.cullen.tatu.model.UserModel;
import com.cullen.tatu.utils.http.JsonCallback;
import com.cullen.tatu.utils.http.ResponseApi;
import com.cullen.tatu.utils.store.SPSUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

public class ApiAccount extends Api {

    public enum Api {
        auth, initInfo, passwordModify,
        passwordModifyEmailSend, update, register

    }



    public String desc(Api api) {
        switch (api) {
            case auth:
                apiDesc = "登录认证";
                break;
            case initInfo:
                apiDesc = "账号信息";
                break;
            case passwordModify:
                apiDesc = "修改密码";
                break;
            case passwordModifyEmailSend:
                apiDesc = "发送重置密码邮件";
                break;
            case update:
                apiDesc = "检查更新";
                break;
            case register:
                apiDesc = "用户注册";
                break;
        }
        return String.format("%s【%s】", apiDesc, this);
    }


    public static String path(Api api) {
        switch (api) {
            case auth:
                return base_url + "api/login";
            case initInfo:
                return base_url + "api/user/info";
            case update:
                return base_url + "api/android";
            case register:
                return base_url + "api/user/register";
        }

        return "";
    }





    public static void initInfo() {
        Log.e("initInfo", "这是第几次调用");
        String url = path(Api.initInfo);
        HttpParams params = new HttpParams();
        OkGo.<String>get(url).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {
                // 如果是 200 就保存用户信息  否则就清除token信息
                // TODO 未来要去刷新TOKEN 信息
                if(response.code == 200) {
//                    UserModel user = JSON.parseObject(response.data, UserModel.class);
                    UserModel user = new Gson().fromJson(response.data.toString(), UserModel.class);
                    authorId = user.getId();
                    SPSUtils.saveUser(user);
                } else {
                    SPSUtils.saveToken(null);
                }
                App.instance.manager.sendBroadcast(new Intent(Constants.ACTION_ACCOUNT_INFO_LOAD));
            }
        });
    }



    public static void auth(String username, String password, JsonCallback callback) {
        String url = path(Api.auth);

        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("password", password);
//        params.put("grant_type", "password");
//        params.put("client_id", "MemberSystem");
//        HttpHeaders httpHeaders = new HttpHeaders("Authorization", "Basic TWVtYmVyU3lzdGVtOjEyMzQ1");
        OkGo.<String>post(url).params(params).execute(callback);
    }



    public static void register(String username, String password, JsonCallback callback) {
        HttpParams params =  new HttpParams();
        params.put("password", password);
        params.put("username", username);
        String url = path(Api.register);
        OkGo.<String>post(url).params(params).execute(callback);
    }

}
