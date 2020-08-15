package com.tatu.essay.utils.http;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.tatu.essay.ui.App;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


/**
 * Created by xieyang on 2017/8/29.
 */

public class HttpUtils {



    /***
     * 初始化okhttp
     */
    public static void initOKHTTP(String tokenString) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);

        builder.hostnameVerifier((String hostname, SSLSession session) -> {
                //强行返回true 即验证成功
                return true;
        });

        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);


        OkGo.getInstance().init(App.instance)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setRetryCount(3)
                .addCommonHeaders(setCommonHttpHeaders(tokenString));

    }


    /**
     * 设置公共的请求头部信息
     * @return
     */
    static HttpHeaders setCommonHttpHeaders(String tokenString) {

        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.HEAD_KEY_USER_AGENT, getUserAgent());

        if (!TextUtils.isEmpty(tokenString)) {
            headers.put("Authorization", tokenString);
        }

        return headers;
    }





    /**
     * 返回正确的UserAgent
     *
     * @return
     */
    static String getUserAgent() {
        StringBuffer sb = new StringBuffer();
        String userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android
        // 6.0.1; vivo X9L Build/MMB29M)

        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }

        Log.v("UserModel-Agent", "UserModel-Agent: " + sb.toString());
        return sb.toString() + " dengtatu";
    }






}
