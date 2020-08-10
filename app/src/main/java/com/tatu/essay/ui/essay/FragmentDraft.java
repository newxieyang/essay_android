package com.tatu.essay.ui.essay;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tatu.essay.R;
import com.tatu.essay.api.Api;
import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.ui.main.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/****
 * 草稿
 */
public class FragmentDraft extends BaseFragment {




    //   列表数据
    private List<EssayModel> data = new ArrayList<>();


    private WeakReference<Activity> activity;

    private EssayAdapter essayAdapter;

    private int pageNum = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = new WeakReference<>(getActivity());
        action = EnumAction.DraftsLoad.getAction();
    }


    @Override
    public void onResume() {
        super.onResume();
        // 是空的时候请求服务器
        requestData();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.clear();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    protected void initView(View view) {


        initAdapter();

    }






    private void initAdapter() {

        List<EssayItem> list = new ArrayList<>();
        essayAdapter = new EssayAdapter((EssayModel essayModel, int position) -> {
            showDetail(essayModel);
        });


        essayAdapter.setFooterView(getLayoutInflater().inflate(R.layout.view_empty_footer,
                null));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(essayAdapter);

        swipeLayout.setRefreshing(true);
        swipeLayout.setNestedScrollingEnabled(true);
        swipeLayout.setOnRefreshListener(this::requestData);


    }


    private void updateUI() {

        if (swipeLayout == null) {
            return;
        }

        swipeLayout.setRefreshing(false);

        if (!data.isEmpty()) {
            List<EssayItem> list;
            list = EssayDataHandler.getList(data);

            essayAdapter.setDiffNewData(list);
        }


    }

    /**
     * 获取文件列表
     */
    private void requestData() {

        EssayApi.drafts();

    }


    public void loadData() {

        data.addAll(EssayService.loadEssays(pageNum));
        pageNum = (int) Math.floor(data.size() / Api.V_PAGE_SIZE);
        updateUI();
    }




}
