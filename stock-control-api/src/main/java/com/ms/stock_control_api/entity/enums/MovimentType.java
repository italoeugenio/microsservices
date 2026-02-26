package com.ms.stock_control_api.entity.enums;

public enum MovimentType {
    QUARTZ,
    AUTOMATIC,
    MANUAL;

    public static MovimentType fromApi(String input){
        switch (input.toUpperCase()){
            case "QUARTZ": return QUARTZ;
            case "AUTOMATIC": return AUTOMATIC;
            case "MANUAL": return MANUAL;
            default: throw new IllegalArgumentException("Invalid input: " + input);
        }
    }

    public String toApi(){
        return this.name();
    }

}
