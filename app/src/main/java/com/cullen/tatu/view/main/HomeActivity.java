package com.cullen.tatu.view.main;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cullen.tatu.R;
import com.cullen.tatu.utils.ApplicationUtils;
import com.cullen.tatu.view.essay.EssayFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationview = findViewById(R.id.navigation_view);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);//初始化状态
        toggle.syncState();


        ViewPager mViewPager = findViewById(R.id.mViewPager);
        HomeActivity.MainAdapter mainAdapter = new HomeActivity.MainAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainAdapter);
        mViewPager.addOnPageChangeListener(mainAdapter);


        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        View headerView = navigationview.getHeaderView(0);
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //寻找头部里面的控件
        ImageView imageView = headerView.findViewById(R.id.iv_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_LONG).show();
            }
        });

/*        //设置item的条目颜色
        navigationview.setItemTextColor(csl);
        //去掉默认颜色显示原来颜色  设置为null显示本来图片的颜色
        navigationview.setItemIconTintList(csl);*/

//        MenuItem preItem = navigationview.i

        navigationview.setNavigationItemSelectedListener((MenuItem item) -> {
            //设置哪个按钮被选中
            item.setChecked(true);

            switch (item.getItemId()) {
                case R.id.item_essay:
                    mViewPager.setCurrentItem(0, false);
                    break;
                case R.id.item_me:
                    mViewPager.setCurrentItem(3, false);
                    break;
            }
            drawer.closeDrawers();
            return false;
        });


        navigationview.findViewById(R.id.footer_item_out).setOnClickListener((View v)-> ApplicationUtils.exitApp());
    }


    private class MainAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

        private List<BaseFragment> fragments = new ArrayList<>();
//        private String[] titles = {"微信", "通讯录", "发现", "我"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new EssayFragment());
            fragments.add(new EssayFragment());
            fragments.add(new DiscoverFragment());
            fragments.add(new MeFragment());
//            fragments.add(TextFragment.newInstance(titles[3]));/**
        }

        @Override
        public BaseFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (0 == position) {
                /*alphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);*/
            } else if (2 == position) {
//                alphaTabsIndicator.getCurrentItemView().removeShow();
            } else if (3 == position) {
//                alphaTabsIndicator.removeAllBadge();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
