package com.tatu.essay.constants;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.tatu.essay.view.App;
import com.tatu.essay.R;

/**
 * Created by xieyang on 2017-11-2.
 * mail: yang.xie@nextcont.com
 */

public class ResourceConstants {


    /*** 黑色 ***/
    public static final int colorDark;
    /*** 白色 ***/
    public static final int colorWhite;
    /*** 灰色 ***/
    public static final int colorGray;
    /*** 黄色 ***/


    static {
        Context context = App.instance;
        colorDark = ContextCompat.getColor(context, R.color.text_dark);
        colorWhite = ContextCompat.getColor(context, R.color.white);
        colorGray = ContextCompat.getColor(context, R.color.text_sliver);

    }



}
