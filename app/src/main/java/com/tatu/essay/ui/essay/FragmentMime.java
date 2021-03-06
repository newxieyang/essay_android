package com.tatu.essay.ui.essay;

import android.os.Bundle;

import com.tatu.essay.api.Api;
import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.ui.main.BaseFragment;

import java.util.List;

import static com.tatu.essay.logic.EnumAction.MineLoad;


/****
 * 草稿
 */

public class FragmentMime extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        action = MineLoad.getAction();
        folder = EnumEssayFolder.MIME;
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void refresh() {
        EssayApi.mime();
    }



    public void loadData() {

        List<EssayModel> list = EssayService.loadMine(pageInfo.page, Api.authorId);;


        swipeLayout.setRefreshing(false);

        if (list.isEmpty()) {
            essayAdapter.setEmptyView(getEmptyDataView());
            return;
        }

        List<EssayItem> items = EssayDataHandler.getList(list);

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
