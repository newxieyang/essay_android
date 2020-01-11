package com.cullen.tatu.view.fleet;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cullen.tatu.R;
import com.cullen.tatu.constants.ItemConstants;
import com.cullen.tatu.model.FleetModel;
import com.vondear.rxtool.RxTimeTool;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class FleetAdapter extends BaseMultiItemQuickAdapter<FleetItem, FleetAdapter.DriveHolder> {


    private FleetOnclickListener listener;


    SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public FleetAdapter(FleetOnclickListener listener, List data) {
        super(data);
        addItemType(ItemConstants.style_list, R.layout.item_fleet_list);
        this.listener = listener;

    }

    @Override
    protected void convert(DriveHolder helper, FleetItem item) {
        fillListFile(helper, item.getModel());
    }


    /***
     * 填充 file list view
     * @param holder
     * @param model
     */
    private void fillListFile(DriveHolder holder, final FleetModel model) {

        holder.setText(R.id.iDepartureText, model.getDeparture());
        holder.setText(R.id.iDestinationText, model.getDestination());

        holder.setText(R.id.iTimeText, getTime(model.getDepartureTime()));

        if (model.getCarNum() != null) {
            holder.setText(R.id.iCarNumText, "车牌：" + model.getCarNum());
        } else {
            holder.setText(R.id.iCarNumText, "");
        }


        String costString = "费用：";
        if (model.getCost() != null) {
            costString += "¥" + model.getCost() + "元";
        } else {
            costString += "面议";
        }
        holder.setText(R.id.iCostText, costString);

        if (model.getVacancy() != null) {
            holder.setText(R.id.iVacancyText, "空位：" + model.getVacancy());
        } else {
            holder.setText(R.id.iVacancyText, "");
        }

        holder.setText(R.id.iContactText, "联系人：" + model.getContact());
        holder.setText(R.id.iPhoneText, "电话：" + model.getPhone());


        if(model.getDescription() != null) {
            holder.setText(R.id.iDescText, "备注：" + model.getDescription());
        } else {
            holder.setText(R.id.iDescText, "");
        }

        holder.itemView.setOnClickListener((View v) ->
                listener.itemClick(model, holder.getLayoutPosition())
        );

    }


    private String getTime(long time) {
        return RxTimeTool.milliseconds2String(time, DEFAULT_SDF);
    }


    class DriveHolder extends BaseViewHolder {
        //
        public DriveHolder(View view) {
            super(view);
        }
    }


}
