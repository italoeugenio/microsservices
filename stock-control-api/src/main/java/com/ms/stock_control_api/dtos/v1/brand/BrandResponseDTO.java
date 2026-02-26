package com.ms.stock_control_api.dtos.v1.brand;

import com.ms.stock_control_api.entity.entities.BrandModel;

public record BrandResponseDTO(
        String name,
        String country,
        int founded

) {
    public BrandResponseDTO(BrandModel brand){
        this(brand.getName(), brand.getCountry(), brand.getFounded());
    }
}
