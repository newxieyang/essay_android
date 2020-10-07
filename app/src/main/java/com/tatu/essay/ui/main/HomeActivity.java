package com.tatu.essay.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.githang.statusbar.StatusBarCompat;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.tatu.essay.R;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.vo.UserVo;
import com.tatu.essay.ui.essay.FragmentDraft;
import com.tatu.essay.ui.essay.FragmentEssay;
import com.tatu.essay.ui.essay.FragmentFavorite;
import com.tatu.essay.ui.essay.FragmentMime;
import com.tatu.essay.ui.setting.SettingActivity;
import com.tatu.essay.utils.ApplicationUtils;
import com.tatu.essay.utils.store.SPSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeActivity extends AppCompatActivity {


    private ViewPager mViewPager;


    private Toolbar toolbar;

    private String nickname;

    private String des;

    private AccountHeader headerResult;

    private Drawer result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StatusBarCompat.setStatusBarColor(this, ResourceConstants.colorWhite);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(savedInstanceState, toolbar);

        toolbar.setTitle(getString(R.string.app_module_essay));
        initViewPage();

    }

    @Override
    protected void onResume() {
        super.onResume();

        preUserInfo();
    }

    private void initViewPage() {
        mViewPager = findViewById(R.id.mViewPager);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new FragmentEssay());
        fragments.add(new FragmentMime());
        fragments.add(new FragmentFavorite());
        fragments.add(new FragmentDraft());
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(homeAdapter);
    }


    private void initDrawer(Bundle savedInstanceState, Toolbar toolbar) {

        buildDrawerHeader(savedInstanceState);

        buildDrawer(savedInstanceState);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelection(0, false);
            toolbar.setTitle(getString(R.string.app_module_essay));

        }
    }


    private void buildDrawerHeader(Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withAccountHeader(R.layout.material_drawer_compact_header)
                .addProfiles(
                        new ProfileDrawerItem().withName(R.string.home_name).withEmail(R.string.home_desc).withIcon(R.mipmap.ic_avatar).withIdentifier(105)
                )
                .withSavedInstance(savedInstanceState)
                .build();

    }


    private void buildDrawer(Bundle savedInstanceState) {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.app_module_essay).withIcon(R.drawable.ic_edit).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.app_module_mime).withIcon(R.drawable.ic_baseline_how_to_reg_24).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.app_module_favorites).withIcon(R.drawable.ic_baseline_favorite_border_24).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.app_module_draft).withIcon(R.drawable.ic_baseline_drafts_24).withIdentifier(3)


                ) // add the items we want to use with our Drawer
                .addStickyDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.app_setting).withIcon(R.drawable.ic_setting).withIdentifier(4).withSelectable(false),
                        new SecondaryDrawerItem().withName(R.string.app_logout).withIcon(R.drawable.ic_out).withIdentifier(5).withSelectable(false)

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Integer identifier = Integer.parseInt("" + drawerItem.getIdentifier());
                            drawerItemClick(identifier);
                        }

                        return false;
                    }
                })

                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
//              .withShowDrawerUntilDraggedOpened(true)
                .build();
    }


    private void drawerItemClick(Integer identifier) {
        if (identifier < 4) {
            mViewPager.setCurrentItem(identifier, false);
            String title = getString(R.string.app_module_essay);
            switch (identifier) {
                case 0:
                    title = getString(R.string.app_module_essay);
                    break;
                case 1:
                    title = getString(R.string.app_module_mime);
                    break;
                case 2:
                    title = getString(R.string.app_module_favorites);
                    break;
                case 3:
                    title = getString(R.string.app_module_draft);
                    break;
            }
            toolbar.setTitle(title);
            return;
        }

        if (identifier == 4) {
            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
        }

        if (identifier == 5) {
            ApplicationUtils.exitApp();
        }
    }


    /***
     * 获取用户信息
     */
    private void preUserInfo() {
        Optional<UserVo> userModel = SPSUtils.loadUser();

        userModel.ifPresent(val -> {
            des = TextUtils.isEmpty(val.getDes()) ? getString(R.string.home_desc) : val.getDes();
            nickname = TextUtils.isEmpty(val.getNickName()) ? getString(R.string.home_name) : val.getNickName();
        });




        headerResult.clear();
        headerResult.addProfile(new ProfileDrawerItem().withName(nickname).withEmail(des).withIcon(R.mipmap.ic_avatar).withIdentifier(105), 0);
    }

}
