package com.mysmallexample.ui;

public enum EnumDemo {
    Enum_One, Enum_Second, Enum_Third, Enum_Fourth, Enum_Fifth, Defult;
    public String options;

    EnumDemo() {
        options = getOption(ordinal());
    }

    private static String getOption(int type) {
        String option;
        switch (type) {
            case 0: {
                option = "Enum_One";
            }
            break;
            case 1: {
                option = "Enum_Second";
            }
            break;
            case 2: {
                option = "Enum_Third";
            }
            break;
            case 3: {
                option = "Enum_Fourth";
            }
            break;
            case 4: {
                option = "Enum_Fifth";
            }
            break;
            default: {
                option = "Defult";
            }
            break;
        }
        return option;
    }
}