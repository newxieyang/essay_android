package com.cullen.tatu.view.main.slider;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 */
public class SliderItem implements MultiItemEntity {

    public enum Type {
        HorizonSplit,
        VerticalSplit,
        Item,
        Footer;

        public static Type fromInt(int i) {
            Type[] values = Type.values();
            for (Type t : values) {
                if (t.ordinal() == i) {
                    return t;
                }
            }
            return null;
        }
    }

    private Type type;

    private SliderMenuItem item;


    public SliderItem(Type itemType) {
        this.type = itemType;
    }

    public SliderItem(Type itemType, SliderMenuItem item) {
        this.type = itemType;
        this.item = item;
    }

    @Override
    public int getItemType() {
        return type.ordinal();
    }

    public Type getType() {
        return type;
    }


    public SliderMenuItem getItem() {
        return item;
    }

    public void setItem(SliderMenuItem item) {
        this.item = item;
    }


}
