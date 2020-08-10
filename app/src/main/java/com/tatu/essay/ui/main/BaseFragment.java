package com.tatu.essay.ui.main;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.githang.statusbar.StatusBarCompat;
import com.tatu.essay.R;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.ui.App;
import com.tatu.essay.ui.essay.EssayDetailActivity;

import java.util.Objects;



public abstract class BaseFragment extends Fragment {

    protected String TAG;

    protected String action;

   protected SwipeRefreshLayout swipeLayout;

    protected RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        StatusBarCompat.setStatusBarColor(Objects.requireNonNull(getActivity()), ResourceConstants.colorWhite);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_essay, container, false);

        swipeLayout = view.findViewById(R.id.swipeLayout);
        recyclerView = view.findViewById(R.id.recyclerView);

        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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

    protected abstract void initView(View view);

    protected abstract void loadData();


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /***
     * 显示随笔详情
     */
    protected void showDetail(EssayModel essayModel) {
        Intent intent = new Intent(getActivity(), EssayDetailActivity.class);
        intent.putExtra("essay", essayModel);
        startActivity(intent);

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
