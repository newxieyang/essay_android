
package com.cullen.tatu.api;

import android.content.Intent;

import com.cullen.tatu.view.App;
import com.cullen.tatu.constants.Constants;
import com.cullen.tatu.model.FleetModel;
import com.cullen.tatu.service.FleetService;
import com.cullen.tatu.utils.http.JsonCallback;
import com.cullen.tatu.utils.http.ResponseApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class ApiFleet extends Api {

    enum Api {
        list, save, update
    }


    public String desc(Api api) {
        switch (api) {
            case list:
                apiDesc = "列表";
                break;
            case save:
                apiDesc = "添加返乡信息";
                break;
            case update:
                apiDesc = "更新返乡信息";
                break;

        }
        return String.format("%s【%s】", apiDesc, this);
    }


    private static String path(Api api) {

        switch (api) {
            case list:
                return fleet_url + "list";
            case save:
                return fleet_url + "save";
            case update:
                return fleet_url + "update";

        }

        return "";
    }


    public static void list() {
        String url = path(Api.list);
        HttpParams params = new HttpParams();
        params.put(PAGE_NUM_STR, 1);
        params.put(PAGE_SIZE_STR, PAGE_SIZE);

        // 以id为开始往后拉， 如果分页有数据就拉去过来
        Long essayId = FleetService.getLastIdEssayId();
        if (essayId != null) {
            params.put("id", essayId);
        }

        OkGo.<String>get(url).params(params).execute(new JsonCallback() {
            @Override
            protected void onResponse(ResponseApi response) {

                List<FleetModel> models = new Gson().fromJson(response.data.get("content").getAsJsonArray(), new TypeToken<List<FleetModel>>() {
                }.getType());

                if (!models.isEmpty()) {
                    FleetService.saveFleet(models);
                }

                App.instance.manager.sendBroadcast(new Intent(Constants.ACTION_FLEET_DATA_LOAD));
            }
        });
    }

    public static void save(HttpParams params, JsonCallback callback) {
        OkGo.<String>post(path(Api.save)).params(params).execute(callback);
    }

    public static void update(int id, JsonCallback callback) {
        String url = path(Api.update) + "/" + id;
        OkGo.<String>get(url).execute(callback);
    }


}
