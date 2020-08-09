package com.tatu.essay.constants;

import androidx.core.content.ContextCompat;

import com.tatu.essay.ui.App;
import com.tatu.essay.R;

/**
 * Created by xieyang on 2017-11-2.
 * mail: yang.xie@nextcont.com
 */

public class ResourceConstants {



    /*** 白色 ***/
    public static final int colorWhite;


    static {
        colorWhite = ContextCompat.getColor(App.instance, R.color.md_white_1000);
    }



}
