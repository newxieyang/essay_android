package com.tatu.essay.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.githang.statusbar.StatusBarCompat;
import com.tatu.essay.R;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.model.EssayModel;
import com.tatu.essay.ui.essay.EssayDetailActivity;

import java.util.Objects;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected String TAG;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        StatusBarCompat.setStatusBarColor(Objects.requireNonNull(getActivity()), ResourceConstants.colorWhite);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_essay, container, false);
        ButterKnife.bind(this, view);
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
    }



    protected abstract void initView(View view);


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }


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
}
