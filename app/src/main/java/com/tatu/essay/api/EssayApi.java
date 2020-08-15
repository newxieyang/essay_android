
package com.tatu.essay.api;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.model.FavoriteModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.service.FavoritesService;
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

    public static final String URL_SAVE = essay_url + "save";

    public static final String URL_UPDATE= essay_url + "update";

    public static final String URL_VIEW = essay_url + "view";

    public static final String URL_ESSAYS_IDS = essay_url + "findByIds";

    
    
    /**
      * @description: 获取essays列表
      * @return ${return_type}
      * @author cullen
      * @date 2020/8/9 21:44 
      */
    public static void essays() {

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

                if(response.code == 200 && response.data != null) {
                    List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                            new TypeToken<List<EssayModel>>() {}.getType());

                    EssayService.saveEssays(models);

                    if (models.size() == PAGE_SIZE) {
                        essays();
                    }
                }

                App.instance.manager.sendBroadcast(new Intent(EnumAction.EssaysLoad.getAction()));

            }
        });

        
    }


    /**
      * @description: 获取收藏列表
      * @return ${return_type}
      * @author cullen
      * @date 2020/8/9 23:40
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

        params.put("userId", Api.authorId);

        OkGo.<String>get(URL_FAVORITES).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                if(response.code == 200 && response.data != null) {
                    List<FavoriteModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                            new TypeToken<List<FavoriteModel>>() {}.getType());

                    FavoritesService.saveEssays(models);

                    if (models.size() == PAGE_SIZE) {
                        favorites();
                    }
                }


                App.instance.manager.sendBroadcast(new Intent(EnumAction.FavoritesLoad.getAction()));

            }
        });


    }


    /**
      * @description: 获取我的essays
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
        params.put("userId", Api.authorId);

        OkGo.<String>get(URL_MINE).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                if(response.code == 200 && response.data != null) {
                    List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                            new TypeToken<List<EssayModel>>() {}.getType());

                    EssayService.saveEssays(models);

                    if (models.size() == PAGE_SIZE) {
                        mime();
                    }
                }


                App.instance.manager.sendBroadcast(new Intent(EnumAction.MineLoad.getAction()));

            }
        });


    }


    /**
      * @description: 获取草稿列表
      * @return ${return_type}
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

        params.put("userId", Api.authorId);

        OkGo.<String>get(URL_DRAFTS).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {


                if(response.data != null) {
                    List<EssayModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(),
                            new TypeToken<List<EssayModel>>() {}.getType());

                    EssayService.saveEssays(models);

                    if (models.size() == PAGE_SIZE) {
                        drafts();
                    }
                }


                App.instance.manager.sendBroadcast(new Intent(EnumAction.DraftsLoad.getAction()));

            }
        });


    }


    /**
      * @description: 保存essay
      * @author cullen
      * @date 2020/8/11 21:38
      */
    public static void save(String jsonParams, JsonCallback callback) {
        OkGo.<String>post(URL_SAVE).upJson(jsonParams).execute(callback);
    }


    /**
      * @description: essay 详情
      * @author cullen
      * @date 2020/8/11 21:39
      */
    public static void view(Long id, JsonCallback callback) {
        String url = URL_VIEW + "/" + id;
        OkGo.<String>get(url).execute(callback);
    }


    /**
      * @description: 更新
      * @author cullen
      * @date 2020/8/11 21:39
      */
    public static void update(long id, HttpParams params, JsonCallback callback) {
        String url = URL_UPDATE+ "/" + id;
        OkGo.<String>put(url).params(params).execute(callback);
    }


    /**
     * 保存收藏
     * @param jsonParams
     * @param callback
     */
    public static void saveFavorite(String jsonParams, JsonCallback callback) {
        OkGo.<String>post(URL_FAVORITE).upJson(jsonParams).execute(callback);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getEssaysByIds(List<Long> ids, JsonCallback callback) {
        String idsString = String.join(",", (CharSequence) ids);
        OkGo.<String>get(URL_ESSAYS_IDS).params("ids", idsString).execute(callback);
    }


}
