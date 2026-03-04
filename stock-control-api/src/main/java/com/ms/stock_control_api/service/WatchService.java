package com.ms.stock_control_api.service;

import com.ms.stock_control_api.dtos.v1.watch.AllWatchsRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchResponseDTO;
import com.ms.stock_control_api.entity.entities.BrandModel;
import com.ms.stock_control_api.entity.entities.WatchModel;
import com.ms.stock_control_api.exception.brand.BrandExceptionNotFound;
import com.ms.stock_control_api.exception.watch.WatchExceptionNotFound;
import com.ms.stock_control_api.mapper.WatchMapper;
import com.ms.stock_control_api.repository.BrandRepository;
import com.ms.stock_control_api.repository.WatchRepository;
import com.ms.stock_control_api.specification.WatchSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WatchService {

    @Autowired
    private WatchRepository watchRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private WatchMapper watchMapper;

    public WatchResponseDTO saveWatch(WatchRequestDTO data){
        WatchModel watchModel = watchMapper.dtoToModel(data);
        WatchModel newWatch = watchRepository.save(watchModel);
        return watchMapper.modelToDto(newWatch);
    }

    public AllWatchsRequestDTO getAll(Integer pageNumber, Integer pageSize, String brand, String movimentType, String boxMaterial, String glassType, Integer minWaterResistance, Integer maxWaterResistance, Long minPrice, Long maxPrice, Float minDiameter, Float maxDiameter, String param){
        if(pageNumber <= 0) throw new IllegalArgumentException("Page number must be greater than zero");
        if(pageSize < 1) throw new IllegalArgumentException("Page size must be greater than zero");
        if(pageSize > 60) throw new IllegalArgumentException("Page size must be less than 60");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, orderBy(param));
        Specification<WatchModel> spec = Specification
                .where(WatchSpecification.brand(brand))
                .and(WatchSpecification.movimentType(movimentType))
                .and(WatchSpecification.boxMaterial(boxMaterial))
                .and(WatchSpecification.glassType(glassType))
                .and(WatchSpecification.minWaterResistanceM(minWaterResistance))
                .and(WatchSpecification.maxWaterResistanceM(maxWaterResistance))
                .and(WatchSpecification.minPrice(minPrice))
                .and(WatchSpecification.maxPrice(maxPrice))
                .and(WatchSpecification.minDiameter(minDiameter))
                .and(WatchSpecification.maxDiameter(maxDiameter));
        Page<WatchResponseDTO> watches = watchRepository.findAll(spec,pageable).map(watch -> watchMapper.modelToDto(watch));

        if(pageNumber > watches.getTotalPages() && watches.getTotalPages() > 0) throw new IllegalArgumentException("Page number must be less than pages or equal: " + watches.getTotalPages());
        return new AllWatchsRequestDTO(watches.getContent(), watches.getTotalPages());
    }

    public WatchResponseDTO getById(UUID id){
        WatchModel watch = watchRepository.findById(id).orElseThrow(() -> new WatchExceptionNotFound(String.valueOf(id)));
        return watchMapper.modelToDto(watch);
    }

    public void delete(UUID id){
        WatchModel watch = watchRepository.findById(id).orElseThrow(() -> new WatchExceptionNotFound(String.valueOf(id)));
        watchRepository.delete(watch);
    }

    public WatchResponseDTO update(UUID id, WatchRequestDTO data) {
        WatchModel watch = watchRepository.findById(id).orElseThrow(() -> new WatchExceptionNotFound(String.valueOf(id)));

        WatchModel watchUpdate = watchMapper.dtoToModel(data);

        watchUpdate.setId(watch.getId());
        watchUpdate.setCreatedAt(watch.getCreatedAt());

        return watchMapper.modelToDto(watchRepository.save(watchUpdate));
    }


    private Sort orderBy(String param){
        switch (param){
            case "price_asc":
                return Sort.by(Sort.Direction.ASC, "priceInCents");
            case "price_desc":
                return Sort.by(Sort.Direction.DESC, "priceInCents");
            case "diameter_asc":
                return Sort.by(Sort.Direction.ASC, "diameterMm");
            case "wr_desc":
                return Sort.by(Sort.Direction.DESC, "waterResistanceM");
            default:
                return Sort.by(Sort.Direction.DESC, "createdAt");
        }
    }


}
