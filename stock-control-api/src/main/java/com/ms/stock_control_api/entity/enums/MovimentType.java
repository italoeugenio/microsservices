package com.ms.stock_control_api.entity.enums;

import com.ms.stock_control_api.exception.enums.EnumExceptionInvalidInput;

import java.util.Arrays;

public enum MovimentType {
    QUARTZ,
    AUTOMATIC,
    MANUAL;

    public static MovimentType fromApi(String input){
        switch (input.toUpperCase()){
            case "QUARTZ": return QUARTZ;
            case "AUTOMATIC": return AUTOMATIC;
            case "MANUAL": return MANUAL;
            default: throw new EnumExceptionInvalidInput("Invalid input: " + input + ". YOU NEED USE THIS OPTIONS: " + Arrays.toString(MovimentType.values()));
        }
    }

    public String toApi(){
        return this.name();
    }

}
