package com.ms.stock_control_api.dtos.v1.watch;

import com.ms.stock_control_api.entity.entities.BrandModel;

import java.util.List;

public record AllWatchsRequestDTO(
        List<WatchResponseDTO> itens,
        int total
) {

}
