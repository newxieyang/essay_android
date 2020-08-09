package com.tatu.essay.ui.essay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tatu.essay.R;
import com.tatu.essay.api.Api;
import com.tatu.essay.api.EssayApi;
import com.tatu.essay.constants.Constants;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.service.EssayService;
import com.tatu.essay.ui.App;
import com.tatu.essay.ui.main.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/****
 * 草稿
 */
public class FragmentDraft extends BaseFragment {


    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.iRecyclerView)
    RecyclerView recyclerView;




    //   列表数据
    private List<EssayModel> data = new ArrayList<>();


    private WeakReference<Activity> activity;

    private EssayAdapter essayAdapter;

    private int pageNum = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = new WeakReference<>(getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.clear();
        App.instance.manager.unregisterReceiver(receiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_draft;
    }


    @Override
    protected void initView(View view) {


        initAdapter();

    }




    @Override
    public void onResume() {
        super.onResume();
        App.instance.manager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_ESSAY_DATA_LOAD));
        // 是空的时候请求服务器
        requestData();

    }


    private void initAdapter() {

        List<EssayItem> list = new ArrayList<>();
        essayAdapter = new EssayAdapter((EssayModel essayModel, int position) -> {
            int truePosition = EssayDataHandler.getPosition(data, essayModel);
            showDetail(truePosition);
        }, list);


        essayAdapter.setFooterView(getLayoutInflater().inflate(R.layout.view_empty_footer,
                null));
        essayAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_essay_empty, null));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(essayAdapter);
        recyclerView.setVisibility(View.GONE);

        swipeLayout.setVisibility(View.VISIBLE);
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

        recyclerView.setVisibility(View.VISIBLE);

    }

    /**
     * 获取文件列表
     */
    private void requestData() {

        EssayApi.drafts();

    }


    private void loadData() {

        data.addAll(EssayService.loadEssay(pageNum, null));
        pageNum = (int) Math.floor(data.size() / Api.V_PAGE_SIZE);
        updateUI();
    }


    /***
     * 显示随笔详情
     */
    private void showDetail(int position) {
        Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
        intent.putExtra("essay", data.get(position));
        startActivity(intent);

    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ACTION_ESSAY_DATA_LOAD.equals(intent.getAction())) {
                loadData();
            }
        }
    };
}