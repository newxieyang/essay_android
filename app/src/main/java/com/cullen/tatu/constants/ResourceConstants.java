package com.cullen.tatu.constants;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;

/**
 * Created by xieyang on 2017-11-2.
 * mail: yang.xie@nextcont.com
 */

public class ResourceConstants {


    /*** 黑色 ***/
    public static final int colorDark;
    /*** 蓝色 ***/
    public static final int colorBlue;
    /*** 银黑色 ***/
    public static final int colorSliverDark;
    /*** 白色 ***/
    public static final int colorWhite;
    /*** 灰色 ***/
    public static final int colorGray;
    /*** 黄色 ***/
    public static final int colorYellow;
    /*** 红色 */
    public static final int colorRed;
    /*** 橘红色 */
    public static final int colorOrange;

    public static final int colorSplit;

    public static final int colorActivityBackground;

    public static final int colorGreen;

    public static final int colorNav;

    public static final int colorSelect;

    public static final int colorSliver;


    static {
        Context context = App.instance;

        colorDark = ContextCompat.getColor(context, R.color.color_text_dark);
        colorBlue = ContextCompat.getColor(context, R.color.app_tint_color);
        colorSliverDark = ContextCompat.getColor(context, R.color.color_text_sliver_dark);
        colorWhite = ContextCompat.getColor(context, R.color.color_text_light_dark);
        colorGray = ContextCompat.getColor(context, R.color.color_text_sliver);
        colorYellow = ContextCompat.getColor(context, R.color.mail_start);
        colorRed = ContextCompat.getColor(context, R.color.color_error);
        colorOrange = ContextCompat.getColor(context, R.color.app_orange);
        colorSplit = ContextCompat.getColor(context, R.color.color_split);
        colorActivityBackground = ContextCompat.getColor(context, R.color.activity_background);
        colorGreen = ContextCompat.getColor(context, R.color.app_green);
        colorNav = ContextCompat.getColor(context, R.color.app_navigation);
        colorSliver = ContextCompat.getColor(context, R.color.color_text_sliver_light);


        colorSelect = ContextCompat.getColor(context,R.color.background_selected);

    }



}
