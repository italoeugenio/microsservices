package com.ms.stock_control_api.service;

import com.ms.stock_control_api.dtos.v1.brand.AllBrandsRequestDTO;
import com.ms.stock_control_api.dtos.v1.brand.BrandDetailsResponseDTO;
import com.ms.stock_control_api.dtos.v1.brand.BrandRequestDTO;
import com.ms.stock_control_api.dtos.v1.brand.BrandResponseDTO;
import com.ms.stock_control_api.entity.entities.BrandModel;
import com.ms.stock_control_api.exception.brand.BrandExceptionNotFound;
import com.ms.stock_control_api.repository.BrandRepostiory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BrandService {

    @Autowired
    private BrandRepostiory brandRepostiory;

    public BrandDetailsResponseDTO saveBrand(BrandRequestDTO data){
        BrandModel brandModel = new BrandModel(data);
        var brand = brandRepostiory.save(brandModel);
        return new BrandDetailsResponseDTO(brand);
    }

    public AllBrandsRequestDTO getAllBrands(Integer pageNumber, Integer founded){
        if(pageNumber <= 0){
            throw new IllegalArgumentException("Page number must be greater than zero");
        }

        Pageable pages = PageRequest.of(pageNumber - 1, 60);
        Page<BrandModel> brands;

        if(founded != null){
            brands = brandRepostiory.findByFounded(founded, pages);
        } else {
            brands = brandRepostiory.findAll(pages);
        }

        int total = brands.getTotalPages();

        if(pageNumber > total){
            throw new IllegalArgumentException("Page number must be less than pages or equal: " + total);
        }

        return new AllBrandsRequestDTO(brands.getContent(), total);
    }

    public BrandResponseDTO getById(UUID id){
        BrandModel brand = brandRepostiory.findById(id).orElseThrow(() -> new BrandExceptionNotFound(String.valueOf(id)));
        return new BrandResponseDTO(brand);
    }

    public void deleteById(UUID id){
        BrandModel brand = brandRepostiory.findById(id).orElseThrow(() -> new BrandExceptionNotFound(String.valueOf(id)));
        brandRepostiory.deleteById(id);
    }

    public BrandResponseDTO updateBrand(UUID id, BrandRequestDTO data){
        BrandModel brand = brandRepostiory.findById(id).orElseThrow(() -> new BrandExceptionNotFound(String.valueOf(id)));
        BeanUtils.copyProperties(data, brand);
        brandRepostiory.save(brand);
        return new BrandResponseDTO(brand);
    }

}
