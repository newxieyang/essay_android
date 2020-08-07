package com.tatu.essay.ui.main;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.snackbar.Snackbar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.tatu.essay.R;
import com.tatu.essay.constants.ResourceConstants;
import com.tatu.essay.ui.essay.FragmentDraft;
import com.tatu.essay.ui.essay.FragmentEssay;
import com.tatu.essay.ui.essay.FragmentFavorite;
import com.tatu.essay.ui.essay.FragmentMime;
import com.tatu.essay.utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    private ViewPager mViewPager;

    private Drawer result = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StatusBarCompat.setStatusBarColor(this, ResourceConstants.colorWhite);

        initViewPage();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer(savedInstanceState, toolbar);

//        (View v)-> ApplicationUtils.exitApp()/**/

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
        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.app_module_essay).withIcon(R.drawable.ic_edit).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.app_module_mime).withIcon(R.drawable.ic_baseline_how_to_reg_24).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.app_module_favorites).withIcon(R.drawable.ic_baseline_favorite_border_24).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.app_module_draft).withIcon(R.drawable.ic_baseline_drafts_24).withIdentifier(3)


                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Integer index = Integer.parseInt("" + drawerItem.getIdentifier());
                            mViewPager.setCurrentItem(index, false);
                            Snackbar.make(view, "Replace with your own action" + position,  Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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
            result.setSelection(1, false);

        }
    }


}
