package com.cullen.tatu.view.main.home;


import com.cullen.tatu.logic.AppModule;
import com.cullen.tatu.view.main.slider.EssayMenuItem;
import com.cullen.tatu.view.main.slider.FleetMenuItem;
import com.cullen.tatu.view.main.slider.SliderItem;
import com.cullen.tatu.view.main.slider.SliderMenuItem;
import com.cullen.tatu.utils.lang.Pair;
import com.cullen.tatu.utils.lang.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * luguangqing
 * 2018/1/16.
 */
public class MainViewModel {

    //    /* 当前应用模块 */
    Variable<AppModule> currentModule = new Variable<>(AppModule.ESSAY);

    // 侧边栏当前菜单列表
    Variable<List<SliderItem>> currentModuleItems = new Variable<>(new ArrayList<>());
    // 侧边栏当前选中的菜单
    Variable<SliderItem> selectedModuleItem = new Variable<>(null);

    Pair<Integer, SliderItem> findFirstItem() {
        List<SliderItem> items = currentModuleItems.getValue();
        long size = items.size();
        for (int i = 0; i < size; i++) {
            SliderItem item = items.get(i);
            SliderItem.Type type = item.getType();
            if (type == SliderItem.Type.Item) {
                return new Pair<>(i, item);
            }
        }
        return null;
    }


    /* 加载模块对应的侧边栏菜单项 */
    void reloadModuleItems() {
        List<SliderItem> items = new ArrayList<>();
        items.add(new SliderItem(SliderItem.Type.HorizonSplit));

        List<SliderMenuItem> defaultsMenuItems = null;

        switch (currentModule.getValue()) {
            case ESSAY:
                defaultsMenuItems = EssayMenuItem.menuItems();
                break;
            case FLEET:
                defaultsMenuItems = FleetMenuItem.menuItems();
                break;

        }

        for (SliderMenuItem item : defaultsMenuItems) {
            items.add(new SliderItem(SliderItem.Type.Item, item));
        }


        currentModuleItems.setValue(items);
    }

}
