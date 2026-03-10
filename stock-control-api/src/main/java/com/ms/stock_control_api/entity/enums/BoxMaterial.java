package com.ms.stock_control_api.entity.enums;

import com.ms.stock_control_api.exception.enums.EnumExceptionInvalidInput;

import java.util.Arrays;

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
            default: throw new EnumExceptionInvalidInput("Invalid input: " + input + ". YOU NEED USE THIS OPTIONS: " + Arrays.toString(BoxMaterial.values()));
        }
    }

    public String toApi(){
        return this.name();
    }
}
