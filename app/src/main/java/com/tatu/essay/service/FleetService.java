package com.tatu.essay.service;

import android.text.TextUtils;

import com.tatu.essay.view.App;
import com.tatu.essay.api.Api;
import com.tatu.essay.model.FleetModel;
import com.tatu.essay.model.gen.FleetModelDao;
import com.tatu.essay.utils.DateUtils;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class FleetService extends BaseService {


    public static List<FleetModel> loadFleet(int pageNum, FleetModel fleet) {
        String condition = "";

        if (fleet.getCreateBy() != null) {
            condition += FleetModelDao.Properties.CreateBy.columnName + "=" + fleet.getCreateBy();
        } else {
            Long time = DateUtils.getCurrentDayMills();
            condition += FleetModelDao.Properties.DepartureTime.columnName + " > " + time;
        }

        // TODO 选择日期， 出发地， 目的地
        if(!TextUtils.isEmpty(fleet.getDeparture())) {
        }

        return App.instance.getFleetDao().queryBuilder()
                .where(new WhereCondition.StringCondition(condition))
                .limit(Api.V_PAGE_SIZE).offset((pageNum - 1) * Api.V_PAGE_SIZE).list();
    }


    public static void saveFleet(List<FleetModel> list) {
        App.instance.getFleetDao().insertOrReplaceInTx(list);
    }


    /**
     * 获取数据库最大的id
     *
     * @return
     */
    public static Long getLastIdEssayId() {
        FleetModel fleetModel = App.instance.getFleetDao().queryBuilder()
                .orderDesc(FleetModelDao.Properties.Id).limit(1).unique();

        if (fleetModel == null) {
            return null;
        }

        return fleetModel.getId();
    }
}
