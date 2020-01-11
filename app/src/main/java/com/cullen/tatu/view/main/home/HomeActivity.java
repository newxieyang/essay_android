package com.cullen.tatu.view.main.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;
import com.cullen.tatu.constants.Constants;
import com.cullen.tatu.logic.AppModule;
import com.cullen.tatu.logic.NavigationStyle;
import com.cullen.tatu.view.main.BaseActivity;
import com.cullen.tatu.view.main.BaseFragment;
import com.cullen.tatu.view.main.LauncherActivity;
import com.cullen.tatu.view.main.slider.SliderItem;
import com.cullen.tatu.view.main.slider.SliderItemOnclickListener;
import com.cullen.tatu.view.main.slider.SliderMenuAdapter;
import com.cullen.tatu.model.UserModel;
import com.cullen.tatu.utils.IconResources;
import com.cullen.tatu.utils.lang.Pair;
import com.cullen.tatu.utils.store.SPSUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.carbs.android.avatarimageview.library.AvatarImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HomeActivity extends BaseActivity implements SliderItemOnclickListener,
        DrawerLayout.DrawerListener {


    @BindView(R.id.iMainDrawer)
    DrawerLayout iMainDrawer;
    @BindView(R.id.menuListView)
    RecyclerView menuListView;
    @BindView(R.id.iNameText)
    TextView iNameText;
    @BindView(R.id.iSignText)
    TextView iSignText;
    @BindView(R.id.iAvatarImage)
    AvatarImageView iAvatarImage;

    @BindView(R.id.iMainFrame)
    FrameLayout iMainFrame;

    @BindView(R.id.iEssayButton)
    ImageView iEssayButton;

    @BindView(R.id.iFleetButton)
    ImageView iFleetButton;

    private MainViewModel viewModel = new MainViewModel();
    private SliderMenuAdapter menuAdapter;
    private BaseFragment currentFragment;
    // 检测到视图元素变化后是否关闭侧边栏
    private boolean shouldCloseDrawer = false;
    // 关闭侧边栏后是否重新加载相应的fragment
    private boolean shouldReloadFragment = false;
    // 第一次点击返回时间
    private long firstPressedTime = 0;
    private List<Disposable> disposables = new ArrayList<>();

    Disposable mDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SPSUtils.loadTokens().isPresent()) {
            startActivity(new Intent(this, LauncherActivity.class));
            finish();
        }
        
        App.instance.manager.registerReceiver(receiver, new IntentFilter(Constants.ACTION_ACCOUNT_INFO_LOAD));


    }

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

        addObserver();

        viewModel.currentModule.setValue(AppModule.ESSAY);

        menuAdapter = new SliderMenuAdapter(viewModel.currentModuleItems.getValue(), this);
        menuAdapter.setEnableLoadMore(false);
        menuListView.setAdapter(menuAdapter);
        menuListView.setLayoutManager(new LinearLayoutManager(this));

        iMainDrawer.addDrawerListener(this);
        viewModel.reloadModuleItems();

        findViewById(R.id.exit).setOnClickListener(view -> logout());

        findViewById(R.id.lineEssay).setOnClickListener(view -> {

            if(AppModule.FLEET.equals(viewModel.currentModule.getValue())) {
                viewModel.currentModule.setValue(AppModule.ESSAY);
            }
        });

        findViewById(R.id.lineFleet).setOnClickListener(view -> {
            if(AppModule.ESSAY.equals(viewModel.currentModule.getValue())) {
                viewModel.currentModule.setValue(AppModule.FLEET);
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeObserver();

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        App.instance.manager.unregisterReceiver(receiver);
    }

    @Override
    protected NavigationStyle navigationStyle() {
        return NavigationStyle.SliderMenu;
    }

    @Override
    protected String initTitleText() {
        return currentTitle();
    }

    @Override
    public void onBackPressed() {
        if (iMainDrawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
            return;
        }

        if (System.currentTimeMillis() - firstPressedTime > 2000) {
            firstPressedTime = System.currentTimeMillis();
        } else {
//            showExitDialog();
        }
    }

    private void addObserver() {
        // 模块变动
        Disposable currentModuleDisposable = viewModel.currentModule.asObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(module -> {
            Log.i("切换至模块%s", module.displayName());
            setModuleIcon(module);
            viewModel.reloadModuleItems();
        });
        disposables.add(currentModuleDisposable);

        // 侧边栏菜单列表项变动
        Disposable currentModuleItemsDisposable = viewModel.currentModuleItems.asObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(sliderItems -> {
            Log.i("侧边栏菜单列表项变动:%s", sliderItems.toString());
            Pair<Integer, SliderItem> first = viewModel.findFirstItem();
            viewModel.selectedModuleItem.setValue(first.getRight());
            menuAdapter.selectPosition = first.getLeft();
            menuAdapter.replaceData(sliderItems);
            if (currentFragment == null) {
                setupFragment(); // 首次进入自动加载fragment
            }
        });
        disposables.add(currentModuleItemsDisposable);

        // 侧边栏选中菜单变动
        Disposable selectedModuleDisposable = viewModel.selectedModuleItem.asObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(item -> {
//            Logger.i("侧边栏选中菜单变动:%s", item);
            menuAdapter.selectPosition = viewModel.currentModuleItems.getValue().indexOf(item);
            menuAdapter.notifyDataSetChanged();
            closeDrawerIfNeed();
            shouldReloadFragment = true;
        });
        disposables.add(selectedModuleDisposable);



    }

    private void removeObserver() {

        for (Disposable d : disposables) {
            d.dispose();
        }
        disposables.clear();
    }

    private void closeDrawerIfNeed() {
        if (shouldCloseDrawer) {
            closeDrawer();
        } else {
            // 防止在侧边栏显示的时候切页面，如模块切换
            if (!iMainDrawer.isDrawerOpen(GravityCompat.START) && shouldReloadFragment) {
                setupFragment();
            }
        }
        shouldCloseDrawer = false;
    }



    //isSub 下级标签
    @Override
    public void onItemClick(int position) {

        SliderItem newItem = viewModel.currentModuleItems.getValue().get(position);

        viewModel.selectedModuleItem.setValue(newItem);
        shouldCloseDrawer = true;
        // 判断是否需要重新加载fragment
        shouldReloadFragment = true;


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        viewModel.currentModule.setValue(viewModel.currentModule.getValue());
//        applyMainDrawerLayout();
    }


    // TODO 未来是否要显示用户头像信息

/*    public void applyUserInfo() {
        UserModel userInfo = SPSUtils.loadUser();
        if (!TextUtils.isEmpty(userInfo.getNickName())) {
            iNameText.setText(userInfo.getNickName());
            iSignText.setText(userInfo.getEmail());
            if (!TextUtils.isEmpty(userInfo.getAvatar())) {
                Glide.with(this).load(userInfo.getAvatar()).into(iAvatarImage);
            }
        }
    }*/


    // ==========================================================
    // Drawer
    // ==========================================================

    public void openDrawer() {
        iMainDrawer.openDrawer(GravityCompat.START);
//        applyUserInfo();
    }

    public void closeDrawer() {
        iMainDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        dismissKeyboard();
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        // 侧边栏关闭
        if (shouldReloadFragment) {
            setupFragment();
            shouldReloadFragment = false;
        }
    }

    public String currentTitle() {
        return viewModel.selectedModuleItem.getValue().getItem().name;
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    private void setupFragment() {

        iMainFrame.setVisibility(View.VISIBLE);
        BaseFragment fragment = viewModel.selectedModuleItem.getValue().getItem().createFragment(currentTitle());
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.iMainFrame, fragment);
            transaction.commitAllowingStateLoss();
            currentFragment = fragment;
        }
    }




    @Override
    public void onFooterViewClick() {

    }


    private void logout() {
        SPSUtils.saveToken(null);
        SPSUtils.saveUser(new UserModel());
        App.instance.removeAll();

        startActivity(new Intent(HomeActivity.this, LauncherActivity
                .class));
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constants.ACTION_ACCOUNT_INFO_LOAD.equals(intent.getAction())) {
//                applyUserInfo();
            }
        }
    };


    private void setModuleIcon(AppModule module) {
        if(AppModule.ESSAY.equals(module)) {
            iEssayButton.setImageDrawable(IconResources.getIconMenuEssaySelect());
            iFleetButton.setImageDrawable(IconResources.getIconMenuFleet());
        }

        if(AppModule.FLEET.equals(module)) {
            iEssayButton.setImageDrawable(IconResources.getIconMenuEssay());
            iFleetButton.setImageDrawable(IconResources.getIconMenuFleetSelect());
        }
    }


}
