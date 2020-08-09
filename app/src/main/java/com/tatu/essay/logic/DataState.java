package com.tatu.essay.logic;

public enum DataState {

    NORMAL(0), // 正常状态
    DISABLE(-1),  // 禁用状态
    DELETE(1),  // 删除标志
    DRAFT(2); // 草稿

    DataState(Integer state) {
        this.state = state;
    }

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


}