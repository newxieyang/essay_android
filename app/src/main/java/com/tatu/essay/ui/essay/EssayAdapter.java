package com.tatu.essay.ui.essay;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tamsiree.rxkit.RxTimeTool;
import com.tatu.essay.R;
import com.tatu.essay.api.Api;
import com.tatu.essay.model.EssayModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 文 件 名: EssayAdapter
 * 创 建 人: Cullen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: newxieyang@126.com
 * 修改时间：
 * 修改备注：
 */
public class EssayAdapter extends BaseQuickAdapter<EssayItem, BaseViewHolder> {


    private EssayOnclickListener listener;


    SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public EssayAdapter(EssayOnclickListener listener) {
        super(R.layout.item_essay_list);
        this.listener = listener;

    }

    @Override
    protected void convert(BaseViewHolder helper, EssayItem item) {
        fillListFile(helper, item.getEssayModel());
    }





    /***
     * 填充 file list view
     * @param holder
     * @param model
     */
    private void fillListFile(BaseViewHolder holder, final EssayModel model) {

        View view = holder.getView(R.id.title);
        if ((model.getTitle() == null)) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            holder.setText(R.id.title, model.getTitle());
        }

        holder.setText(R.id.content, model.getContent());

//        holder.setText(R.id.iTimeText, getTime(model.getCreateTime()));

        holder.itemView.setOnClickListener((View v) ->
                listener.itemClick(model, holder.getLayoutPosition())
        );

    }




    private String getName(long authorId) {
        return Api.authorId == authorId ? getContext().getString(R.string.home_name) : "主角2" + authorId;
    }


    private String getTime(long time) {
        return RxTimeTool.milliseconds2String(time, DEFAULT_SDF);
    }







}
