package com.tatu.essay.ui.essay;

import android.os.Bundle;

import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.ui.main.BaseFragment;

import static com.tatu.essay.logic.EnumAction.MineLoad;


/****
 * 草稿
 */

public class FragmentMime extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = MineLoad.getAction();
        folder = EnumEssayFolder.MIME;
    }


    @Override
    protected void refresh() {
        EssayApi.mime();
    }


}
