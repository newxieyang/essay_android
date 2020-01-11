package com.cullen.tatu.view.essay;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cullen.tatu.R;
import com.cullen.tatu.api.Api;
import com.cullen.tatu.constants.ItemConstants;
import com.cullen.tatu.model.EssayModel;
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
public class EssayAdapter extends BaseMultiItemQuickAdapter<EssayItem, EssayAdapter.DriveHolder> {


    private EssayOnclickListener listener;


    SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public EssayAdapter(EssayOnclickListener listener, List data) {
        super(data);
        addItemType(ItemConstants.style_grid, R.layout.item_essay_grid);
        addItemType(ItemConstants.style_list, R.layout.item_essay_list);
        this.listener = listener;

    }

    @Override
    protected void convert(DriveHolder helper, EssayItem item) {
        switch (helper.getItemViewType()) {

            case ItemConstants.style_list:
                fillListFile(helper, item.getEssayModel());
                break;

            case ItemConstants.style_grid:
                fillGridFile(helper, item.getEssayModel());
                break;
        }
    }





    /***
     * 填充 file list view
     * @param holder
     * @param model
     */
    private void fillListFile(DriveHolder holder, final EssayModel model) {

        holder.setText(R.id.iNameText, model.getContent());

        holder.setText(R.id.iDescText, getName(model.getCreateBy()));

        holder.setText(R.id.iTimeText, getTime(model.getCreateTime()));

        holder.itemView.setOnClickListener((View v) ->
                listener.itemClick(model, holder.getLayoutPosition())
        );

    }


    /***
     * 填充 file grid view
     * @param holder
     * @param model
     */
    private void fillGridFile(DriveHolder holder, EssayModel model) {

        holder.setText(R.id.iNameText, model.getContent());

        holder.setText(R.id.iDescText, getName(model.getCreateBy()));

        holder.setText(R.id.iTimeText, getTime(model.getCreateTime()));

        holder.itemView.setOnClickListener((View v) ->
                listener.itemClick(model, holder.getLayoutPosition())
        );

    }


    private String getName(long authorId) {
        return Api.authorId == authorId ? mContext.getString(R.string.home_name) : "主角" + authorId;
    }


    private String getTime(long time) {
        return RxTimeTool.milliseconds2String(time, DEFAULT_SDF);
    }


    class DriveHolder extends BaseViewHolder {
        public DriveHolder(View view) {
            super(view);
        }
    }


}
