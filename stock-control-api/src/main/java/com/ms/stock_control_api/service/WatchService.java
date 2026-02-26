package com.ms.stock_control_api.service;

import com.ms.stock_control_api.dtos.v1.watch.AllWatchsRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchResponseDTO;
import com.ms.stock_control_api.entity.entities.WatchModel;
import com.ms.stock_control_api.mapper.WatchMapper;
import com.ms.stock_control_api.repository.WatchRepository;
import com.ms.stock_control_api.specification.WatchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class WatchService {

    @Autowired
    private WatchRepository watchRepository;

    @Autowired
    private WatchMapper watchMapper;

    public WatchResponseDTO saveWatch(WatchRequestDTO data){
        WatchModel watchModel = watchMapper.dtoToModel(data);
        WatchModel newWatch = watchRepository.save(watchModel);
        return watchMapper.modelToDto(newWatch);
    }

    public AllWatchsRequestDTO getAll(Integer pageNumber, Integer pageSize, String brand){
        if(pageNumber <= 0) throw new IllegalArgumentException("Page number must be greater than zero");
        if(pageSize < 1) throw new IllegalArgumentException("Page size must be greater than zero");
        if(pageSize > 60) throw new IllegalArgumentException("Page size must be less than 60");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Specification<WatchModel> spec = Specification
                .where(WatchSpecification.brand(brand));
        Page<WatchResponseDTO> watches = watchRepository.findAll(spec,pageable).map(watch -> watchMapper.modelToDto(watch));

        if(pageNumber > watches.getTotalPages() && watches.getTotalPages() > 0) throw new IllegalArgumentException("Page number must be less than pages or equal: " + watches.getTotalPages());
        return new AllWatchsRequestDTO(watches.getContent(), watches.getTotalPages());
    }

}
