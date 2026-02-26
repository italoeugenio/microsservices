package com.ms.stock_control_api.entity.enums;

public enum BoxMaterial {
    STEEL,
    TITANIUM,
    RESIN,
    BRONZE,
    CERAMIC;

    public static BoxMaterial fromAPi(String input){
        switch (input.toUpperCase()){
            case "STEEL": return STEEL;
            case "TITANIUM": return TITANIUM;
            case "RESIN": return RESIN;
            case "BRONZE": return BRONZE;
            case "CERAMIC": return CERAMIC;
            default: throw new IllegalArgumentException("Invalid input: " + input);
        }
    }

    public String toApi(){
        return this.name();
    }
}
