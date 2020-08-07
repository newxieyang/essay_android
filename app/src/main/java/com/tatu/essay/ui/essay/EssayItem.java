package com.tatu.essay.ui.essay;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tatu.essay.model.EssayModel;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class EssayItem implements MultiItemEntity {


    private int itemType;
    private int spanSize;

    private EssayModel essayModel;


    public EssayItem(int itemType, int spanSize, EssayModel essayModel) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.essayModel = essayModel;
    }



    public int getSpanSize() {
        return spanSize;
    }


    @Override
    public int getItemType() {
        return itemType;
    }

    public EssayModel getEssayModel() {
        return essayModel;
    }


}
