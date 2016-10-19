package com.mysmallexample.ui;

public enum EnumTypeDemo {

    Enum_One(1),
    Enum_Second(2),
    Enum_Third(3),
    Enum_Fourth(4),
    Enum_Fifth(5),
    Defult(6);

    private int type;

    EnumTypeDemo(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}