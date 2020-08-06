package com.tatu.essay.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;

import com.tatu.essay.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;


public class DiscoverFragment extends BaseFragment {



    @BindView(R.id.activity_header)
    Toolbar toolbar;


    private WeakReference<Activity> activity;


    public String title;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = new WeakReference<>(getActivity());


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
    protected int initLayout() {
        return R.layout.fragment_discover;
    }


    @Override
    protected void initView(View view) {

        toolbar.setTitle(R.string.app_module_discover);








    }


    @Override
    public void onResume() {
        super.onResume();




    }





}
