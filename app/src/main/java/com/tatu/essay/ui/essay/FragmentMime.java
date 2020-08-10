package com.tatu.essay.ui.essay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.tatu.essay.R;
import com.tatu.essay.api.Api;
import com.tatu.essay.api.EssayApi;
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
        super.onCreate(savedInstanceState);
        action = MineLoad.getAction();
    }


    @Override
    public void onResume() {
        super.onResume();
        // 是空的时候请求服务器
        refresh();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @Override
    protected void initView(View view) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initAdapter();
        initRefreshLayout();
//        initLoadMore();

        // 新建按钮
        view.findViewById(R.id.fab).setOnClickListener(view1 ->
                startActivity(new Intent(getActivity(), EssayCreateActivity.class))
        );

    }





    /**
     * 初始化加载更多
     */
/*    private void initLoadMore() {
        // load more
        essayAdapter.getLoadMoreModule().setOnLoadMoreListener(this::loadData);
        essayAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        essayAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }*/


    /**
     * 获取文件列表
     */
    public void refresh() {

        EssayApi.mime();

    }


    public void loadData() {


        swipeLayout.setRefreshing(false);
        List<EssayModel> list = EssayService.loadMine(pageInfo.page, Api.authorId);
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

        if (list.size() < PageInfo.PAGE_SIZE) {
            //如果不够一页,显示没有更多数据布局
//            essayAdapter.getLoadMoreModule().loadMoreEnd();
            Snackbar.make(getView(), "没有更多数据", Snackbar.LENGTH_LONG).show();
        } else {
            essayAdapter.getLoadMoreModule().loadMoreComplete();
        }

        // page加一
        pageInfo.nextPage();
    }





}
