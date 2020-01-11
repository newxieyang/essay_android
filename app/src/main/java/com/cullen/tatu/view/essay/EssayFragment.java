package com.cullen.tatu.view.essay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;
import com.cullen.tatu.api.Api;
import com.cullen.tatu.api.ApiEssay;
import com.cullen.tatu.constants.Constants;
import com.cullen.tatu.logic.EssayFolder;
import com.cullen.tatu.logic.ListLayoutStyle;
import com.cullen.tatu.view.main.BaseFragment;
import com.cullen.tatu.view.main.home.HomeActivity;
import com.cullen.tatu.view.main.slider.EssayMenuItem;
import com.cullen.tatu.model.EssayModel;
import com.cullen.tatu.service.EssayService;
import com.cullen.tatu.utils.IconResources;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.RxRecyclerViewDividerTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class EssayFragment extends BaseFragment {


    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.iRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.activity_header)
    Toolbar toolbar;

    private RxRecyclerViewDividerTool itemDecoration;


    //   列表数据
    private List<EssayModel> data = new ArrayList<>();


    private WeakReference<Activity> activity;


    public EssayMenuItem menuItem;

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
        return R.layout.fragment_essay;
    }


    @Override
    protected void initView(View view) {


        itemDecoration = new RxRecyclerViewDividerTool(RxImageTool.dp2px(3f));

        // 新建按钮
        fab.setOnClickListener(view1 ->
                startActivity(new Intent(getActivity(), EssayCreateActivity.class))
        );


        toolbar.setTitle(R.string.app_module_essay);

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

        essayAdapter.setSpanSizeLookup(
                (GridLayoutManager gridLayoutManager, int position) ->
                        list.get(position).getSpanSize()
        );

        essayAdapter.setFooterView(getLayoutInflater().inflate(R.layout.view_empty_footer,
                null));
        essayAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_essay_empty, null));

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(essayAdapter);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setVisibility(View.GONE);

        swipeLayout.setVisibility(View.VISIBLE);
        swipeLayout.setRefreshing(true);
        swipeLayout.setNestedScrollingEnabled(true);
        swipeLayout.setColorSchemeResources(
                R.color.loading_one,
                R.color.loading_two,
                R.color.loading_th,
                R.color.loading_four);

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

            essayAdapter.replaceData(list);
        }

        recyclerView.setVisibility(View.VISIBLE);

    }

    /**
     * 获取文件列表
     */
    private void requestData() {

        ApiEssay.listRecent();

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
