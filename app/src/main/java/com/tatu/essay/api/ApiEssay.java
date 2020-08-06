
package com.tatu.essay.api;

import android.content.Intent;

import com.tatu.essay.view.App;
import com.tatu.essay.constants.Constants;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class ApiEssay extends Api {

    enum Api {
        list, save, view, favorite, favorites, draft, drafts
    }


    public String desc(Api api) {
        switch (api) {
            case list:
                apiDesc = "列表";
                break;
            case save:
                apiDesc = "添加随笔";
                break;
            case view:
                apiDesc = "随笔详情";
                break;

        }
        return String.format("%s【%s】", apiDesc, this);
    }


    private static String path(Api api) {
        switch (api) {
            case list:
                return essay_url + "list";
            case save:
                return essay_url + "save";
            case view:
                return essay_url + "view";
        }

        return "";
    }


    /***
     *
     */
    public static void listRecent() {

        HttpParams params = new HttpParams();
        params.put(PAGE_NUM_STR, 1);
        params.put(PAGE_SIZE_STR, PAGE_SIZE);

        // 以id为开始往后拉， 如果分页有数据就拉去过来
        EssayModel essay = EssayService.getLastEssay();
        if (essay != null) {
            params.put("createTime", essay.getCreateTime());
        }

        String url = path(Api.list);
        OkGo.<String>get(url).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                        new TypeToken<List<EssayModel>>() {}.getType());

                EssayService.saveEssays(models);

                if (models.size() == PAGE_SIZE) {
                    listRecent();
                }

                App.instance.manager.sendBroadcast(new Intent(Constants.ACTION_ESSAY_DATA_LOAD));

            }
        });

        
    }


    public static void save(String content, JsonCallback callback) {
        String url = path(Api.save);
        HttpParams params = new HttpParams();
        params.put("content", content);
        params.put("authorId", authorId);
        OkGo.<String>post(url).params(params).execute(callback);
    }

    public static void view(int id, JsonCallback callback) {
        String url = path(Api.view) + "/" + id;
        OkGo.<String>get(url).execute(callback);
    }


}