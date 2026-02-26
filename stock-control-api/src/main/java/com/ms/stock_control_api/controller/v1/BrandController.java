package com.ms.stock_control_api.controller.v1;


import com.ms.stock_control_api.dtos.v1.brand.AllBrandsRequestDTO;
import com.ms.stock_control_api.dtos.v1.brand.BrandDetailsResponseDTO;
import com.ms.stock_control_api.dtos.v1.brand.BrandRequestDTO;
import com.ms.stock_control_api.dtos.v1.brand.BrandResponseDTO;
import com.ms.stock_control_api.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public ResponseEntity<AllBrandsRequestDTO> getAllBrands(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false) Integer founded
    ) {
        var brands = brandService.getAllBrands(pageNumber,founded);
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }

    @PostMapping("/")
    public ResponseEntity<BrandDetailsResponseDTO> saveBrand(@Valid @RequestBody BrandRequestDTO data){
        var brand = brandService.saveBrand(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(brand);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getById(@PathVariable("id") UUID id){
        var brand = brandService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(brand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id){
        brandService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public  ResponseEntity<BrandResponseDTO> updateBrand(@PathVariable("id") UUID id, @RequestBody BrandRequestDTO data){
        return ResponseEntity.status(HttpStatus.OK).body(brandService.updateBrand(id, data));
    }
}
