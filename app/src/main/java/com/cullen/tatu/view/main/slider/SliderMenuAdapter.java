package com.cullen.tatu.view.main.slider;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cullen.tatu.R;
import com.cullen.tatu.constants.ResourceConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SliderMenuAdapter extends BaseMultiItemQuickAdapter<SliderItem, SliderMenuAdapter.ViewHolder> {

    SliderItemOnclickListener itemOnclickListener;
    public int selectPosition = 0;

    public SliderMenuAdapter(List<SliderItem> data, SliderItemOnclickListener itemOnclickListener) {
        super(data);
        addItemType(SliderItem.Type.HorizonSplit.ordinal(), R.layout.view_horiz_split);
        addItemType(SliderItem.Type.VerticalSplit.ordinal(), R.layout.view_split_line);
        addItemType(SliderItem.Type.Item.ordinal(), R.layout.item_slider);
        addItemType(SliderItem.Type.Footer.ordinal(), R.layout.item_slider_foot);
//        addItemType(SliderItem.Type.ChatState.ordinal(), R.layout.item_chat_statue);
        this.itemOnclickListener = itemOnclickListener;
    }

    @Override
    protected void convert(SliderMenuAdapter.ViewHolder holder, SliderItem item) {
        SliderItem.Type type = SliderItem.Type.fromInt(holder.getItemViewType());
        if (type == null) {
            return;
        }
        switch (type) {
            case HorizonSplit:
            case VerticalSplit:
                break;
            case Item:
                fillView(holder, item.getItem());
                break;
            case Footer:
                holder.itemView.setOnClickListener((View view) ->
                        itemOnclickListener.onFooterViewClick()
                );
                break;
//            case ChatState:
//                itemOnclickListener.onBlindChatStateView();
//                break;
        }
    }

    private void fillView(SliderMenuAdapter.ViewHolder viewHolder, SliderMenuItem item) {
        viewHolder.iIconImage.setImageDrawable(item.icon);
        viewHolder.iTitleText.setText(item.name);

        viewHolder.itemView.setOnClickListener((View v) -> {
            itemOnclickListener.onItemClick(viewHolder.getAdapterPosition());
            Log.i("", "click:" + item.name);
        });

        if (viewHolder.getAdapterPosition() == selectPosition) {
            viewHolder.itemView.setBackgroundColor(ResourceConstants.colorSelect);
        } else {
            viewHolder.itemView.setBackgroundColor(ResourceConstants.colorNav);
        }

    }

    class ViewHolder extends BaseViewHolder {
        @Nullable
        @BindView(R.id.iIconImage)
        ImageView iIconImage;
        @Nullable
        @BindView(R.id.iTitleText)
        TextView iTitleText;



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
