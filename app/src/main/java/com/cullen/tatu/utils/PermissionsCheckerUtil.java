package com.cullen.tatu.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

/**
 * Created by xieyang on 2017/5/4.
 */

public class PermissionsCheckerUtil {

    private final Context mContext;

    public PermissionsCheckerUtil(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public boolean lacksPermissions(String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
            if (this.lacksPermission(permission)) {
                return true;
            }
        }

        return false;
    }

    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(this.mContext, permission) == -1;
    }
}
