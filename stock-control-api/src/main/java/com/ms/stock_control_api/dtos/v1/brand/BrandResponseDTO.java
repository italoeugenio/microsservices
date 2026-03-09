package com.ms.stock_control_api.dtos.v1.brand;

import com.ms.stock_control_api.entity.entities.BrandModel;

import java.time.LocalDateTime;
import java.util.UUID;

public record BrandResponseDTO(
        UUID id,
        String name,
        String country,
        int founded,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public BrandResponseDTO(BrandModel brand){
        this(brand.getId(),brand.getName(), brand.getCountry(), brand.getFounded(), brand.getCreatedAt(), brand.getUpdatedAt());
    }
}
