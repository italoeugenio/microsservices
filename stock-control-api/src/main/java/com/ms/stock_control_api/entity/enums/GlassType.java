package com.ms.stock_control_api.entity.enums;

public enum GlassType {
    MINERAL,
    SAPPHIRE,
    ACRYLIC;

    public static GlassType fromApi(String input){
        switch (input.toUpperCase()){
            case "MINERAL": return MINERAL;
            case "SAPPHIRE": return SAPPHIRE;
            case "ACRYLIC": return ACRYLIC;
            default: throw new IllegalArgumentException("Invalid input: " + input);
        }
    }

    public String toApi(){
        return this.name();
    }
}
