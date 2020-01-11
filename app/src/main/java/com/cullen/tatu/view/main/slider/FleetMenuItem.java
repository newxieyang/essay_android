package com.cullen.tatu.view.main.slider;

import android.content.Context;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;
import com.cullen.tatu.logic.AppModule;
import com.cullen.tatu.logic.FleetFolder;
import com.cullen.tatu.view.main.BaseFragment;
import com.cullen.tatu.utils.DrawableUtils;
import com.cullen.tatu.view.fleet.FleetFragment;

import java.util.ArrayList;
import java.util.List;

import static com.cullen.tatu.constants.ResourceConstants.colorSliverDark;


public class FleetMenuItem extends SliderMenuItem {


    public FleetFolder folder;

    @Override
    public BaseFragment createFragment(String title) {
        FleetFragment fragment = new FleetFragment();
        fragment.menuItem = this;
        fragment.title = title;
        return fragment;
    }



//    @NonNull
    public static List<SliderMenuItem> menuItems() {
        List<SliderMenuItem> items = new ArrayList<>();
        Context mContext = App.instance;
        for (FleetFolder folder : FleetFolder.listValues()) {
            FleetMenuItem item = new FleetMenuItem();
            String nameRes = "";
            int iconRes = 0;
            String id = "";
            switch (folder) {
                case All:
                    nameRes = "所有车队信息";
                    iconRes = R.mipmap.ic_mail_folder_type_inbox;
                    id = FleetFolder.All.name();
                    break;
                case Me:
                    nameRes = "我发布的信息";
                    iconRes = R.mipmap.unread_label;
                    id = FleetFolder.Me.name();
                    break;

            }
            item.name = nameRes;
            item.icon = DrawableUtils.tintDrawable(mContext, iconRes, colorSliverDark);
            item.id = id;
            item.module = AppModule.ESSAY;
            item.folder = folder;
            items.add(item);
        }
        return items;
    }

}
