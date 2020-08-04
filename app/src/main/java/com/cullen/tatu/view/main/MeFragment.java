package com.cullen.tatu.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cullen.tatu.R;
import com.cullen.tatu.utils.ApplicationUtils;

import java.lang.ref.WeakReference;


public class MeFragment extends BaseFragment {


    private WeakReference<Activity> activity;


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
        return R.layout.fragment_me;
    }


    @Override
    protected void initView(View view) {


    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
