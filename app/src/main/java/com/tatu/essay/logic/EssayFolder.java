package com.tatu.essay.logic;


public enum EssayFolder {

    All, Me;

    public static EssayFolder[] listValues() {
        return new EssayFolder[]{All, Me};
    }
}