package com.tatu.essay.service;

import com.tatu.essay.api.Api;
import com.tatu.essay.model.FavoriteModel;
import com.tatu.essay.ui.App;

import java.util.List;

public class FavoritesService extends BaseService {





    /***
     * 加载数据
     * @param pageNum
     * @return
     */
    public static List<FavoriteModel> loadFavorites(int pageNum) {

        return App.instance.getFavoriteDao().queryBuilder()
                .limit(Api.V_PAGE_SIZE).offset((pageNum - 1) * Api.V_PAGE_SIZE).list();

    }


    public static void saveEssays(List<FavoriteModel> list) {
        App.instance.getFavoriteDao().insertOrReplaceInTx(list);
    }
}
