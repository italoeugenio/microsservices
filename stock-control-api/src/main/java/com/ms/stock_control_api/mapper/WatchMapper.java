package com.ms.stock_control_api.mapper;

import com.ms.stock_control_api.dtos.v1.watch.WatchRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchResponseDTO;
import com.ms.stock_control_api.entity.entities.WatchModel;
import com.ms.stock_control_api.entity.enums.BoxMaterial;
import com.ms.stock_control_api.entity.enums.GlassType;
import com.ms.stock_control_api.entity.enums.MovimentType;
import com.ms.stock_control_api.entity.enums.WaterLabel;
import com.ms.stock_control_api.exception.brand.BrandExceptionNotFound;
import com.ms.stock_control_api.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WatchMapper {

    @Autowired
    private BrandRepository brandRepository;

    public WatchResponseDTO modelToDto(WatchModel watchModel){
        return new WatchResponseDTO(
                watchModel.getId(),
                watchModel.getBrand().getName(),
                watchModel.getModel(),
                watchModel.getReference(),
                watchModel.getMovimentType().toApi(),
                watchModel.getBoxMaterial().toApi(),
                watchModel.getGlassType().toApi(),
                watchModel.getWaterResistanceM(),
                watchModel.getDiameterMm(),
                watchModel.getLugTolugMm(),
                watchModel.getThicknessMm(),
                watchModel.getLugWidthMm(),
                watchModel.getPriceInCents(),
                watchModel.getImage(),
                watchModel.getWaterResistanceLabel(),
                watchModel.getCollectorScore()
        );
    }

    public WatchModel dtoToModel(WatchRequestDTO dto) {
        WatchModel watch = new WatchModel();

        var brand = brandRepository.findById(dto.brandId())
                .orElseThrow(() -> new BrandExceptionNotFound(String.valueOf(dto.brandId())));

        watch.setBrand(brand);
        watch.setModel(dto.model());
        watch.setReference(dto.reference());
        watch.setMovimentType(MovimentType.fromApi(dto.movimentType()));
        watch.setBoxMaterial(BoxMaterial.fromAPi(dto.boxMaterial()));
        watch.setGlassType(GlassType.fromApi(dto.glassType()));
        watch.setWaterResistanceM(dto.waterResistanceM());
        watch.setDiameterMm(dto.diameterMm());
        watch.setLugTolugMm(dto.lugToLugMm());
        watch.setThicknessMm(dto.thicknessMm());
        watch.setLugWidthMm(dto.lugWidthMm());
        watch.setPriceInCents(dto.priceInCents());
        watch.setImage(dto.image());
        watch.setWaterResistanceLabel(this.checkWaterResistanceLabel(dto));
        watch.setCollectorScore(this.checkColllectorScore(dto));
        return watch;
    }

    private String checkWaterResistanceLabel(WatchRequestDTO watchRequestDTO){
        if (watchRequestDTO.waterResistanceM() < 50) {
            return WaterLabel.RESISTANT.getDEscription();
        } else if (watchRequestDTO.waterResistanceM() <= 99) {
            return WaterLabel.DAILY_USE.getDEscription();
        } else if (watchRequestDTO.waterResistanceM() <= 199) {
            return WaterLabel.SWIMMING.getDEscription();
        } else {
            return WaterLabel.DAILY_USE.getDEscription();
        }
    }


    private long checkColllectorScore(WatchRequestDTO watchRequestDTO){
        long score = 0;

        if (watchRequestDTO.glassType().equals(GlassType.SAPPHIRE.toApi())) score += 25;
        if (watchRequestDTO.waterResistanceM() >= 100) score += 15;
        if (watchRequestDTO.waterResistanceM() >= 200) score += 10;
        if (MovimentType.fromApi(watchRequestDTO.movimentType()).equals(MovimentType.AUTOMATIC)) score += 20;
        if (BoxMaterial.fromAPi(watchRequestDTO.boxMaterial()).equals(BoxMaterial.STEEL)) score += 10;
        if (BoxMaterial.fromAPi(watchRequestDTO.boxMaterial()).equals(BoxMaterial.TITANIUM)) score += 12;
        if(watchRequestDTO.diameterMm() >= 38 && watchRequestDTO.diameterMm() <= 42) score += 8;

        return score;
    }

}
