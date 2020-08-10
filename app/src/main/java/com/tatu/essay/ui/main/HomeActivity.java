package com.tatu.essay.ui.main;


import android.os.Bundle;
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
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.ui.essay.FragmentDraft;
import com.tatu.essay.ui.essay.FragmentEssay;
import com.tatu.essay.ui.essay.FragmentFavorite;
import com.tatu.essay.ui.essay.FragmentMime;
import com.tatu.essay.utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private ViewPager mViewPager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StatusBarCompat.setStatusBarColor(this, ResourceConstants.colorWhite);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer(savedInstanceState, toolbar);
        initViewPage();

    }



    private void initViewPage() {
        mViewPager = findViewById(R.id.mViewPager);

        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new FragmentEssay());
        fragments.add(new FragmentMime());
        fragments.add(new FragmentDraft());
        fragments.add(new FragmentFavorite());
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(homeAdapter);
    }


    private void initDrawer(Bundle savedInstanceState, Toolbar toolbar) {


        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withAccountHeader(R.layout.material_drawer_compact_header)
                .addProfiles(
                        new ProfileDrawerItem().withName(R.string.home_name).withEmail(R.string.home_desc).withIcon(R.mipmap.ic_avatar).withIdentifier(105)
                )
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        Drawer result = new DrawerBuilder()
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

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelection(0, false);

        }
    }


    private void drawerItemClick(Integer identifier) {
        if(identifier < 4) {
            mViewPager.setCurrentItem(identifier, false);
            return;
        }

        if(identifier == 4) {

        }

        if(identifier == 5) {
            ApplicationUtils.exitApp();
        }
    }

}
