package com.tatu.essay.logic;

public enum EnumAction {

    // 账号信息
    AccountLoad("android.intent.action.essay.account.load"),
    // 随笔
    EssaysLoad("android.intent.action.essay.essays.load.data"),
    // 我的
    MineLoad("android.intent.action.essay.mine.load.data"),
    // 收藏
    FavoritesLoad("android.intent.action.essay.favorites.load.data"),
    // 草稿
    DraftsLoad("android.intent.action.essay.drafts.load.data");

    private String action;

    EnumAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }




}
