package com.ms.stock_control_api.dtos.v1.brand;

import com.ms.stock_control_api.entity.entities.BrandModel;

import java.util.List;

public record AllBrandsRequestDTO(
        List<BrandModel> itens,
        int total
) {

}
