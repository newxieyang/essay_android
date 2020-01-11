package com.cullen.tatu.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.graphics.drawable.DrawableCompat;

import com.cullen.tatu.R;


/**
 * Created by xieyang on 2017-11-2.
 * mail: yang.xie@nextcont.com
 */

public class DrawableUtils {


    /***
     * 生成icon
     * @param context
     * @param resId
     * @param colorId
     * @return
     */
    public static Drawable createDrawable(Context context, int resId, int colorId) {

        int icon_size = (int) context.getResources().getDimension(R.dimen.icon_size);

        Drawable drawable = resizeDrawable(context, resId, icon_size,
                icon_size);

        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);

        ColorStateList colors = ColorStateList.valueOf(colorId);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }





    public static Drawable resizeDrawable(Context context, int resId, int widthDp, int heightDp) {
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), resId);
//        int width = AppHelper.dip2px(c, widthDp);
//        int height = AppHelper.dip2px(c, heightDp);
        Bitmap b = Bitmap.createScaledBitmap(original, widthDp, heightDp, false);
        Drawable d = new BitmapDrawable(context.getResources(), b);
        return d;
    }

    public static Drawable resizeDrawable(Context context, int resId) {
        int icon_size = (int) context.getResources().getDimension(R.dimen.icon_size);
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), resId);
//        int width = AppHelper.dip2px(c, widthDp);
//        int height = AppHelper.dip2px(c, heightDp);
        Bitmap b = Bitmap.createScaledBitmap(original, icon_size, icon_size, false);
        Drawable d = new BitmapDrawable(context.getResources(), b);
        return d;
    }


    public static Drawable tintDrawable(Context context,int drawableId, int colorValue) {
        Resources res = context.getResources();
        Drawable drawable = res.getDrawable(drawableId).mutate();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);

        ColorStateList colors = ColorStateList.valueOf(colorValue);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

}
