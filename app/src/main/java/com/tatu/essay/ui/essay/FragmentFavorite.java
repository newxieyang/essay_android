package com.tatu.essay.ui.essay;

import android.os.Bundle;

import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.model.FavoriteModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.service.FavoritesService;
import com.tatu.essay.ui.main.BaseFragment;

import java.util.List;
import java.util.stream.Collectors;

import static com.tatu.essay.logic.EnumAction.FavoritesLoad;

/****
 *
 * 喜欢
 */
public class FragmentFavorite extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        action = FavoritesLoad.getAction();
        folder = EnumEssayFolder.FAVORITES;
        super.onCreate(savedInstanceState);
    }


    /**
     * 从服务器拉数据
     */
    public void refresh() {
        EssayApi.favorites();
    }


    public void loadData() {

        List<FavoriteModel> list = FavoritesService.loadFavorites(pageInfo.page);

        swipeLayout.setRefreshing(false);

        if (list.isEmpty()) {
            essayAdapter.setEmptyView(getEmptyDataView());
            return;
        }

        List<Long> ids = list.stream().map(FavoriteModel ::getEssayId).collect(Collectors.toList());

        List<EssayModel> essayModels = EssayService.loadFavorites(ids);

        List<EssayItem> items = EssayDataHandler.getList(essayModels);

        if (pageInfo.isFirstPage()) {
            essayAdapter.setList(items);
        } else {
            essayAdapter.addData(items);
        }

/*        if (list.size() < PageInfo.PAGE_SIZE) {
            //如果不够一页,显示没有更多数据布局
//            essayAdapter.getLoadMoreModule().loadMoreEnd();
//            Snackbar.make(getView(), "没有更多数据", Snackbar.LENGTH_LONG).show();
            return;
        } else {
            essayAdapter.getLoadMoreModule().loadMoreComplete();
        }*/

        // page加一
        if (list.size() == PageInfo.PAGE_SIZE) {
            pageInfo.nextPage();
        }
    }

}
