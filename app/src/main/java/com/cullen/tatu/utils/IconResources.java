package com.cullen.tatu.utils;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;

import static com.cullen.tatu.constants.ResourceConstants.colorDark;
import static com.cullen.tatu.constants.ResourceConstants.colorGray;
import static com.cullen.tatu.constants.ResourceConstants.colorWhite;

public class IconResources {

    private static Drawable icon_navigation_back;
    private static Drawable icon_navigation_menu;
    private static Drawable icon_layout_list;
    private static Drawable icon_layout_grid;

    private static Context context;

    static {
        context = App.instance;
    }

    /***
     * 导航菜单
     * @return
     */
    public static Drawable getNavigationMenuIcon() {
        if (icon_navigation_menu == null) {
            icon_navigation_menu = DrawableUtils.createDrawable(context, R.mipmap.ic_slider, colorDark);
        }
        return icon_navigation_menu;
    }

    /***
     * 返回
     * @return
     */
    public static Drawable getNavigationBackIcon() {
        if (icon_navigation_back == null) {
            icon_navigation_back = DrawableUtils.createDrawable(context, R.mipmap.ic_nav_left, colorDark);
        }
        return icon_navigation_back;
    }





    /***
     * 标签设置
     * @return
     */
    private static Drawable icon_label_setting;

    public static Drawable getLabelSettingIcon() {
        if (icon_label_setting == null) {
            icon_label_setting = DrawableUtils.createDrawable(context, R.mipmap.ic_setting, colorWhite);
        }
        return icon_label_setting;
    }




    private static Drawable icon_close;

    public static Drawable getCloseIcon() {
        if (icon_close == null) {
            icon_close = DrawableUtils.createDrawable(context, R.mipmap.ic_delete, colorDark);
        }
        return icon_close;
    }

    private static Drawable icon_white_close;

    public static Drawable getWhiteCloseIcon() {
        if (icon_white_close == null) {
            icon_white_close = DrawableUtils.createDrawable(context, R.mipmap.ic_delete, colorWhite);
        }
        return icon_white_close;
    }


    private static Drawable icon_ok;

    public static Drawable getOkIcon() {
        if (icon_ok == null) {
            icon_ok = DrawableUtils.createDrawable(context, R.mipmap.ic_right, colorDark);
        }
        return icon_ok;
    }



    private static Drawable icon_white_ok;

    public static Drawable getWhiteOkIcon() {
        if (icon_white_ok == null) {
            icon_white_ok = DrawableUtils.createDrawable(context, R.mipmap.ic_right, colorWhite);
        }
        return icon_white_ok;
    }

    private static Drawable icon_back;

    public static Drawable getBackIcon() {
        if (icon_back == null) {
            icon_back = DrawableUtils.createDrawable(context, R.mipmap.ic_nav_left, colorDark);
        }
        return icon_back;
    }

    private static Drawable icon_menu_exit;

    public static Drawable getMenuExitIcon() {
        if (icon_menu_exit == null) {
            icon_menu_exit = DrawableUtils.createDrawable(context, R.mipmap.ic_exit, colorGray);
        }
        return icon_menu_exit;
    }


    /***
     * 网盘菜单 list
     * @return
     */
    public static Drawable getMenuListIcon() {
        if (icon_layout_list == null) {
            icon_layout_list = DrawableUtils.createDrawable(context, R.mipmap.ic_menu_list, colorDark);
        }
        return icon_layout_list;
    }

    /***
     * 网盘菜单 grid
     * @return
     */
    public static Drawable getMenuGridIcon() {
        if (icon_layout_grid == null) {
            icon_layout_grid = DrawableUtils.createDrawable(context, R.mipmap.ic_menu_grid, colorDark);
        }
        return icon_layout_grid;
    }


    private static Drawable ic_menu_essay;

    public static Drawable getIconMenuEssay() {
        if (ic_menu_essay == null) {
            ic_menu_essay = DrawableUtils.resizeDrawable(context, R.mipmap
                    .ic_essay);
        }
        return ic_menu_essay;
    }

    private static Drawable ic_menu_essay_select;

    public static Drawable getIconMenuEssaySelect() {
        if (ic_menu_essay_select == null) {
            ic_menu_essay_select = DrawableUtils.resizeDrawable(context, R.mipmap
                    .ic_essay_select);
        }
        return ic_menu_essay_select;
    }


    private static Drawable ic_menu_fleet;

    public static Drawable getIconMenuFleet() {
        if (ic_menu_fleet == null) {
            ic_menu_fleet = DrawableUtils.resizeDrawable(context, R.mipmap
                    .ic_fleet);
        }
        return ic_menu_fleet;
    }

    private static Drawable ic_menu_fleet_select;

    public static Drawable getIconMenuFleetSelect() {
        if (ic_menu_fleet_select == null) {
            ic_menu_fleet_select = DrawableUtils.resizeDrawable(context, R.mipmap
                    .ic_fleet_select);
        }
        return ic_menu_fleet_select;
    }
}
