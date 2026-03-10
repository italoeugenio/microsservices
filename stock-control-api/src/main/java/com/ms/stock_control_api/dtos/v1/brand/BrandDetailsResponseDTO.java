package com.ms.stock_control_api.dtos.v1.brand;

import com.ms.stock_control_api.entity.entities.BrandModel;

import java.util.UUID;

public record BrandDetailsResponseDTO(
        UUID id,
        String name,
        String country,
        int founded

) {
    public BrandDetailsResponseDTO(BrandModel brand){
        this(brand.getId(), brand.getName(), brand.getCountry(), brand.getFounded());
    }
}
