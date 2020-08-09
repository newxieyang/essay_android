
package com.tatu.essay.api;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tatu.essay.constants.Constants;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.ui.App;
import com.tatu.essay.utils.http.JsonCallback;
import com.tatu.essay.utils.http.ResponseApi;

import java.util.List;



public class EssayApi extends Api {


    public static final String URL_ESSAYS = essay_url + "essays";

    public static final String URL_FAVORITES = essay_url + "favorites";

    public static final String URL_DRAFTS = essay_url + "drafts";

    public static final String URL_MINE = essay_url + "mine";

    public static final String URL_FAVORITE = essay_url + "favorite";

    public static final String URL_DRAFT = essay_url + "draft";

    public static final String URL_SAVE = essay_url + "save";


    public static final String URL_VIEW = essay_url + "view";

    public static final String URL_UPDATE= essay_url + "update";

    
    
    /**
      * @description: TODO
      * @return ${return_type}
      * @author cullen
      * @date 2020/8/9 21:44 
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

        OkGo.<String>get(URL_ESSAYS).params(params).execute(new JsonCallback() {
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


    /***
     *
     */
    public static void favorites() {

        HttpParams params = new HttpParams();
        params.put(PAGE_NUM_STR, 1);
        params.put(PAGE_SIZE_STR, PAGE_SIZE);

        // 以id为开始往后拉， 如果分页有数据就拉去过来
        EssayModel essay = EssayService.getLastEssay();
        if (essay != null) {
            params.put("createTime", essay.getCreateTime());
        }

        OkGo.<String>get(URL_ESSAYS).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                        new TypeToken<List<EssayModel>>() {}.getType());

                EssayService.saveEssays(models);

                if (models.size() == PAGE_SIZE) {
                    favorites();
                }

                App.instance.manager.sendBroadcast(new Intent(Constants.ACTION_ESSAY_DATA_LOAD));

            }
        });


    }


    /**
      * @description: TODO
      * @return ${return_type}
      * @author cullen
      * @date 2020/8/9 21:44
      */
    public static void mime() {

        HttpParams params = new HttpParams();
        params.put(PAGE_NUM_STR, 1);
        params.put(PAGE_SIZE_STR, PAGE_SIZE);

        // 以id为开始往后拉， 如果分页有数据就拉去过来
        EssayModel essay = EssayService.getLastEssay();
        if (essay != null) {
            params.put("createTime", essay.getCreateTime());
        }

        OkGo.<String>get(URL_ESSAYS).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                        new TypeToken<List<EssayModel>>() {}.getType());

                EssayService.saveEssays(models);

                if (models.size() == PAGE_SIZE) {
                    mime();
                }

                App.instance.manager.sendBroadcast(new Intent(Constants.ACTION_ESSAY_DATA_LOAD));

            }
        });


    }


    /**
      * @description: TODO
      * @param ${tags}
      * @return ${return_type}
      * @throws
      * @author cullen
      * @date 2020/8/9 21:43
      */
    public static void drafts() {

        HttpParams params = new HttpParams();
        params.put(PAGE_NUM_STR, 1);
        params.put(PAGE_SIZE_STR, PAGE_SIZE);

        // 以id为开始往后拉， 如果分页有数据就拉去过来
        EssayModel essay = EssayService.getLastEssay();
        if (essay != null) {
            params.put("createTime", essay.getCreateTime());
        }

        OkGo.<String>get(URL_ESSAYS).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                        new TypeToken<List<EssayModel>>() {}.getType());

                EssayService.saveEssays(models);

                if (models.size() == PAGE_SIZE) {
                    drafts();
                }

                App.instance.manager.sendBroadcast(new Intent(Constants.ACTION_ESSAY_DATA_LOAD));

            }
        });


    }


    public static void save(String jsonParams, JsonCallback callback) {
        OkGo.<String>post(URL_SAVE).upJson(jsonParams).execute(callback);
    }

    public static void view(Long id, JsonCallback callback) {
        String url = URL_VIEW + "/" + id;
        OkGo.<String>get(url).execute(callback);
    }


    public static void update(long id, String JsonParams, JsonCallback callback) {
        String url = URL_UPDATE+ "/" + id;
        OkGo.<String>post(url).upJson(JsonParams).execute(callback);
    }


}
