package com.cullen.tatu.utils.http;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by xieyang on 2017/7/6.
 * 处理请求中的401, 需要重新登录
 */

public abstract class JsonCallback extends StringCallback {


    @Override
    public void onSuccess(Response<String> response) {

        onResponse(new ResponseApi(response));
/*        if (response.code() != 401) {
        } else {
//            String header = response.headers().get("Authorization");
            String header = response.getRawCall().request().header("Authorization");
            if (TextUtils.isEmpty(header)) {
                return;
            }

            // TODO exitApp
//            ApplicationUtils.exitApp(LogoutType.AuthError);

        }*/

    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
//        if (TextUtils.isEmpty(response.getException().getMessage()) && TextUtils.isEmpty(response.body())) {
//        } else {
//            Log.e("response", response.getException().getMessage());
//            onSuccess(response);
//        }

        if(response.code() != 0) {
            onSuccess(response);
        } else {
            // TODO handler error
        }
    }

    protected abstract void onResponse(ResponseApi response);
}
