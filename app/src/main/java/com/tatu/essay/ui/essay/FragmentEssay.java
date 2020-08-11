package com.tatu.essay.ui.essay;

import android.os.Bundle;

import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.ui.main.BaseFragment;


/****
 * 所有
 */

public class FragmentEssay extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = EnumAction.EssaysLoad.getAction();
        folder = EnumEssayFolder.ESSAYS;
    }


    /**
     * 从服务器拉数据
     */
    public void refresh() {
        EssayApi.essays();
    }


}
