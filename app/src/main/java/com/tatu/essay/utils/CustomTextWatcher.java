package com.tatu.essay.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by xieyang on 2017-12-13.
 * mail: yang.xie@nextcont.com
 */

public abstract class CustomTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public abstract void afterTextChanged(Editable editable);
}
