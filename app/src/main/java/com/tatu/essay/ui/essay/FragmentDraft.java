package com.tatu.essay.ui.essay;

import android.os.Bundle;

import com.tatu.essay.api.EssayApi;
import com.tatu.essay.logic.EnumAction;
import com.tatu.essay.logic.EnumEssayFolder;
import com.tatu.essay.ui.main.BaseFragment;

/****
 * 草稿
 */
public class FragmentDraft extends BaseFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        action = EnumAction.DraftsLoad.getAction();
        folder = EnumEssayFolder.DRAFTS;
    }


    /**
     * 从服务器拉数据
     */
    public void refresh() {
        EssayApi.drafts();
    }


}
