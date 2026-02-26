package com.ms.stock_control_api.dtos.v1.watch;

import java.util.UUID;

public record WatchResponseDTO(
        UUID id,
        String brand,
        String model,
        String reference,
        String movimentType,
        String boxMaterial,
        String glassType,
        int waterResistanceM,
        float diameterMm,
        float lugToLugMm,
        float thicknessMm,
        float lugWidthMm,
        long priceInCents,
        String image,
        String waterResistanceLabel,
        long collectorScore
) {
}
