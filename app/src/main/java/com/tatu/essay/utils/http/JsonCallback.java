package com.tatu.essay.utils.http;

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


    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);


        if(response.code() != 0) {
            onSuccess(response);
        } else {
            // TODO handler error
        }
    }

    protected abstract void onResponse(ResponseApi response);
}
