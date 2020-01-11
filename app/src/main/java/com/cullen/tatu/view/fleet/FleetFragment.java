package com.cullen.tatu.view.fleet;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;
import com.cullen.tatu.api.Api;
import com.cullen.tatu.api.ApiFleet;
import com.cullen.tatu.constants.Constants;
import com.cullen.tatu.logic.FleetFolder;
import com.cullen.tatu.view.main.BaseFragment;
import com.cullen.tatu.view.main.home.HomeActivity;
import com.cullen.tatu.view.main.slider.FleetMenuItem;
import com.cullen.tatu.model.FleetModel;
import com.cullen.tatu.service.FleetService;
import com.cullen.tatu.utils.IconResources;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vondear.rxtool.RxImageTool;
import com.vondear.rxtool.RxRecyclerViewDividerTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FleetFragment extends BaseFragment {


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
    private List<FleetModel> data = new ArrayList<>();


    private WeakReference<Activity> activity;


    public String title;


    public FleetMenuItem menuItem;

    private FleetAdapter adapter;

    private int pageNum = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = new WeakReference<>(getActivity());

        App.instance.manager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_FLEET_DATA_LOAD));

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
        return R.layout.fragment_fleet;
    }


    @Override
    protected void initView(View view) {


        itemDecoration = new RxRecyclerViewDividerTool(RxImageTool.dp2px(3f));

        initAdapter();


        // 新建按钮
        fab.setOnClickListener(view1 ->
                startActivity(new Intent(getActivity(), FleetCreateActivity.class))
        );



      toolbar.setTitle(R.string.app_module_fleet);


    }


    @Override
    public void onResume() {
        super.onResume();

        requestData();

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(v ->
                    ((HomeActivity) getActivity()).openDrawer()
            );
        }


    }


    private void initAdapter() {



        List<FleetItem> list = new ArrayList<>();

        adapter = new FleetAdapter((FleetModel essayModel, int position) -> {
            int truePosition = FleetDataHandler.getPosition(data, essayModel);
//            showDetail(truePosition);
        }, list);

        adapter.setSpanSizeLookup(
                (GridLayoutManager gridLayoutManager, int position) ->
                        list.get(position).getSpanSize()
        );

        adapter.setFooterView(getLayoutInflater().inflate(R.layout.view_empty_footer,
                null));
        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.layout_essay_empty, null));


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
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

        swipeLayout.setRefreshing(false);

        if (!data.isEmpty()) {
            List<FleetItem> list = FleetDataHandler.getList(data);
            adapter.replaceData(list);
        }

        recyclerView.setVisibility(View.VISIBLE);

    }


    /**
     * 获取文件列表
     */
    private void requestData() {

        ApiFleet.list();

    }


    private void loadData() {

        FleetModel fleetModel = new FleetModel();

     /*   if (FleetFolder.Me.equals(menuItem.folder)) {
            fleetModel.setCreateBy(Api.authorId);
        }*/
        data.addAll(FleetService.loadFleet(pageNum, fleetModel));
        pageNum++;

        updateUI();
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ACTION_FLEET_DATA_LOAD.equals(intent.getAction())) {
                loadData();
            }
        }
    };


}
