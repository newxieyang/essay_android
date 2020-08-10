package com.tatu.essay.logic;


public enum EnumEssayFolder {

    ESSAYS, MIME, FAVORITES, DRAFTS;

    public static EnumEssayFolder[] listValues() {
        return new EnumEssayFolder[]{ESSAYS, MIME, FAVORITES, DRAFTS};
    }
}
