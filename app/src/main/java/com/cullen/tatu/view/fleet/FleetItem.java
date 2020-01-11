package com.cullen.tatu.view.fleet;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cullen.tatu.model.FleetModel;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class FleetItem implements MultiItemEntity {



    private int itemType;
    private int spanSize;

    private FleetModel model;


    public FleetItem(int itemType, int spanSize, FleetModel model) {
        this.itemType = itemType;
        this.spanSize = spanSize;
        this.model = model;
    }


    public int getSpanSize() {
        return spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public FleetModel getModel() {
        return model;
    }

    public void setModel(FleetModel model) {
        this.model = model;
    }


}
