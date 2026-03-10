package com.ms.stock_control_api.entity.enums;

import com.ms.stock_control_api.exception.enums.EnumExceptionInvalidInput;

import java.util.Arrays;

public enum GlassType {
    MINERAL,
    SAPPHIRE,
    ACRYLIC;

    public static GlassType fromApi(String input){
        switch (input.toUpperCase()){
            case "MINERAL": return MINERAL;
            case "SAPPHIRE": return SAPPHIRE;
            case "ACRYLIC": return ACRYLIC;
            default: throw new EnumExceptionInvalidInput("Invalid input: " + input + ". YOU NEED USE THIS OPTIONS: " + Arrays.toString(GlassType.values()));        }
    }

    public String toApi(){
        return this.name();
    }
}
