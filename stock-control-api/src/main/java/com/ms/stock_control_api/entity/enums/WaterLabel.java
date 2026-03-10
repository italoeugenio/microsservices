package com.ms.stock_control_api.entity.enums;

public enum WaterLabel {
    RESISTANT("splash resistant"),
    DAILY_USE("daily use"),
    SWIMMING("suitable for swimming"),
    DIVING("suitable for diving");

    private String description;

    WaterLabel(String description){
        this.description = description;
    }

    public String getDEscription(){
        return this.description;
    }

}
