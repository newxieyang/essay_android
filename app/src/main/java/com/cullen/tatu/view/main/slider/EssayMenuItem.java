package com.cullen.tatu.view.main.slider;

import android.content.Context;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;
import com.cullen.tatu.logic.AppModule;
import com.cullen.tatu.logic.EssayFolder;
import com.cullen.tatu.view.main.BaseFragment;
import com.cullen.tatu.utils.DrawableUtils;
import com.cullen.tatu.view.essay.EssayFragment;

import java.util.ArrayList;
import java.util.List;

import static com.cullen.tatu.constants.ResourceConstants.colorSliverDark;


public class EssayMenuItem extends SliderMenuItem {


    public EssayFolder folder;


    @Override
    public BaseFragment createFragment(String title) {
        EssayFragment fragment = new EssayFragment();
        fragment.menuItem = this;
        return fragment;
    }



//    @NonNull
    public static List<SliderMenuItem> menuItems() {
        List<SliderMenuItem> items = new ArrayList<>();
        Context mContext = App.instance;
        for (EssayFolder folder : EssayFolder.listValues()) {
            EssayMenuItem item = new EssayMenuItem();
            String nameRes = "";
            int iconRes = 0;
            String id = "";
            switch (folder) {
                case All:
                    nameRes = "他/她人的历程";
                    iconRes = R.mipmap.ic_mail_folder_type_inbox;
                    id = EssayFolder.All.name();
                    break;
                case Me:
                    nameRes = "我的历程";
                    iconRes = R.mipmap.unread_label;
                    id = EssayFolder.Me.name();
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
