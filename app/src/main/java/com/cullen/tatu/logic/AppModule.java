package com.cullen.tatu.logic;

import androidx.annotation.NonNull;

import com.cullen.tatu.view.App;
import com.cullen.tatu.R;

public enum AppModule {

    ESSAY,
    FLEET;

    @NonNull
    public String displayName() {
        switch (this){
            case ESSAY:
                return App.instance.getString(R.string.app_module_essay);
            case FLEET:
                return App.instance.getString(R.string.app_module_fleet);

        }
        return "";
    }
}
