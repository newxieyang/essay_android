package com.tatu.essay.view.main;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.navigation.NavigationView;
import com.tatu.essay.R;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.utils.ApplicationUtils;
import com.tatu.essay.view.essay.FragmentDraft;
import com.tatu.essay.view.essay.FragmentEssay;
import com.tatu.essay.view.essay.FragmentFavorite;
import com.tatu.essay.view.essay.FragmentMe;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout drawer;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StatusBarCompat.setStatusBarColor(this, ResourceConstants.colorWhite);

//        Toolbar toolbar = findViewById(R.id.toolbar).findViewById(R.id.activity_header);
        NavigationView navigationview = findViewById(R.id.navigation_view);
        drawer = findViewById(R.id.drawer_layout);

//        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, 0, 0);
//        drawer.addDrawerListener(toggle);//初始化状态
//        toggle.syncState();


        mViewPager = findViewById(R.id.mViewPager);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new FragmentEssay());
        fragments.add(new FragmentMe());
        fragments.add(new FragmentDraft());
        fragments.add(new FragmentFavorite());
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(homeAdapter);


        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        View headerView = navigationview.getHeaderView(0);
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigation_view_header);

        //寻找头部里面的控件
    /*    ImageView imageView = headerView.findViewById(R.id.iv_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_LONG).show();
            }
        });*/



        navigationview.setNavigationItemSelectedListener((MenuItem item) -> {

            selectDrawerItem(item);
            return false;
        });

//         默认选中
//        navigationview.setCheckedItem(R.id.item_essay);

        navigationview.findViewById(R.id.footer_item_out).setOnClickListener((View v)-> ApplicationUtils.exitApp());
    }




    public void selectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_essay:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.item_me:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.item_draft:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.item_favorite:
                mViewPager.setCurrentItem(3, false);
                break;
        }
//        item.setChecked(true);
        drawer.closeDrawers();
    }


}
