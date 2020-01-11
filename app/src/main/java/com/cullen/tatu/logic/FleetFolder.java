package com.cullen.tatu.logic;


public enum FleetFolder {

    All, Me;

    public static FleetFolder[] listValues() {
        return new FleetFolder[]{All, Me};
    }
}
