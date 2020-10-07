package com.tatu.essay.ui.main;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.githang.statusbar.StatusBarCompat;
import com.tatu.essay.R;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.ui.App;
import com.tatu.essay.ui.essay.EssayAdapter;
import com.tatu.essay.ui.essay.EssayCreateActivity;
import com.tatu.essay.ui.essay.EssayDetailActivity;
import com.tatu.essay.ui.essay.PageInfo;

import java.util.Objects;

import spencerstudios.com.bungeelib.Bungee;


public abstract class BaseFragment extends Fragment {

    protected String TAG;

    protected String action;

    protected EnumEssayFolder folder;

    protected SwipeRefreshLayout swipeLayout;

    protected RecyclerView recyclerView;

    protected PageInfo pageInfo = new PageInfo();

    protected EssayAdapter essayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        StatusBarCompat.setStatusBarColor(Objects.requireNonNull(getActivity()), ResourceConstants.colorWhite);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_essay, container, false);

        initView(view);
        initAdapter();
        initRefreshLayout();

//        initLoadMore()

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.instance.manager.unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        App.instance.manager.registerReceiver(receiver, new IntentFilter(action));
    }


    protected abstract void refresh();


    protected abstract void loadData();


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void initView(View view) {

        swipeLayout = view.findViewById(R.id.swipeLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        View fab = view.findViewById(R.id.fab);
        fab.setVisibility(folder.equals(EnumEssayFolder.ESSAYS)?View.VISIBLE:View.GONE);

        // 新建按钮
        view.findViewById(R.id.fab).setOnClickListener(view1 ->
                startActivity(new Intent(getActivity(), EssayCreateActivity.class))
        );
    }



    protected void initRefreshLayout() {
        swipeLayout.setColorSchemeColors(Color.rgb(66, 165, 245));
        swipeLayout.setOnRefreshListener(this::refresh);
    }

    protected void initAdapter() {

        essayAdapter = new EssayAdapter((EssayModel essayModel, int position) -> showDetail(essayModel));

//        essayAdapter.setFooterView(getLayoutInflater().inflate(R.layout.view_empty_footer,
//                recyclerView));
        recyclerView.setAdapter(essayAdapter);

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
    public View getEmptyDataView() {
        View notDataView = getLayoutInflater().inflate(R.layout.view_empty, recyclerView, false);
        notDataView.setOnClickListener(v -> refresh());
        return notDataView;
    }


    /***
     * 显示随笔详情
     */
    protected void showDetail(EssayModel essayModel) {
        Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
        intent.putExtra("essay", essayModel);
        startActivity(intent);
        Bungee.shrink(getContext());
    }





    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (action.equals(intent.getAction())) {
                loadData();
            }
        }
    };
}
