package com.tatu.essay.ui.essay;

import com.tatu.essay.constants.ItemConstants;
import com.tatu.essay.model.EssayModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyang on 2017-12-12.
 * mail: yang.xie@nextcont.com
 */

public class EssayDataHandler {


    public static ArrayList<EssayItem> getList(List<EssayModel> files) {
        ArrayList<EssayItem> list = new ArrayList<>();

        for (EssayModel driveFile : files) {
            list.add(new EssayItem(ItemConstants.style_list, ItemConstants
                    .ONE_SPAN_SIZE, driveFile));
        }

        return list;
    }


    public static List<EssayItem> getGridList(List<EssayModel> files) {
        List<EssayItem> list = new ArrayList<>();

        for (EssayModel driveFile : files) {
            list.add(new EssayItem(ItemConstants.style_grid, ItemConstants
                    .TWO_SPAN_SIZE, driveFile));
        }

        return list;

    }


    public static int getPosition(List<EssayModel> essayModels, EssayModel essayModel) {
        for (int i = 0; i < essayModels.size(); i++) {
            if (essayModels.get(i).getId().equals(essayModel.getId())) {
                return i;
            }
        }
        return 0;
    }


}
