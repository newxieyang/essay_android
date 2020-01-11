package com.cullen.tatu.view.fleet;

import com.cullen.tatu.constants.ItemConstants;
import com.cullen.tatu.model.FleetModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyang on 2017-12-12.
 * mail: yang.xie@nextcont.com
 */

public class FleetDataHandler {


    public static ArrayList<FleetItem> getList(List<FleetModel> array) {
        ArrayList<FleetItem> list = new ArrayList<>();

        for (FleetModel item : array) {
            list.add(new FleetItem(ItemConstants.style_list, ItemConstants
                    .ONE_SPAN_SIZE, item));
        }

        return list;
    }


    public static int getPosition(List<FleetModel> essayModels, FleetModel essayModel) {
        for (int i = 0; i < essayModels.size(); i++) {
            if (essayModels.get(i).getId().equals(essayModel.getId())) {
                return i;
            }
        }
        return 0;
    }


}
