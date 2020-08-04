package com.cullen.tatu.view.main;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.cullen.tatu.R;
import com.cullen.tatu.utils.ApplicationUtils;
import com.cullen.tatu.view.essay.FragmentDraft;
import com.cullen.tatu.view.essay.FragmentEssay;
import com.cullen.tatu.view.essay.FragmentFavorite;
import com.cullen.tatu.view.essay.FragmentMe;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout drawer;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationview = findViewById(R.id.navigation_view);
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);//初始化状态
        toggle.syncState();


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
        ImageView imageView = headerView.findViewById(R.id.iv_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_LONG).show();
            }
        });

//        //设置item的条目颜色
//        navigationview.setItemTextColor(csl);
//        //去掉默认颜色显示原来颜色  设置为null显示本来图片的颜色
//        navigationview.setItemIconTintList(csl);

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
