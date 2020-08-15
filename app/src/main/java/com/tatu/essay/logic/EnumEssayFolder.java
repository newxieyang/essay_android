package com.tatu.essay.logic;


public enum EnumEssayFolder {

    ESSAYS("随笔"), MIME("我的"), FAVORITES("收藏"), DRAFTS("草稿");


    EnumEssayFolder(String des) {
        this.des = des;
    }

    private String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static EnumEssayFolder[] listValues() {
        return new EnumEssayFolder[]{ESSAYS, MIME, FAVORITES, DRAFTS};
    }
}
