package com.cullen.tatu.view.main.slider;


import android.graphics.drawable.Drawable;

import com.cullen.tatu.logic.AppModule;
import com.cullen.tatu.view.main.BaseFragment;


public abstract class SliderMenuItem {

    public String name = "";

    public String id = "";


    public Drawable icon;


    public AppModule module = AppModule.ESSAY;


    public abstract BaseFragment createFragment(String title);

}
