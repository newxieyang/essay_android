package com.tatu.essay.utils.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.model.Response;

public class ResponseApi {

    public int code;
    public JsonObject data;
    public String errMsg = "";

    public ResponseApi(Response<String> response) {
        if(response.code() == 200) {

            Result result = new Gson().fromJson(response.body(), Result.class);
            code = result.getCode();
            data = result.getData();
            errMsg = result.getMessage();

        } else {
            code = response.code();

            if (response.getException() != null && !TextUtils.isEmpty(response.getException().getMessage())) {
                Log.e("response", response.getException().getMessage());
                errMsg = response.getException().getMessage();
            }
        }
    }
}
