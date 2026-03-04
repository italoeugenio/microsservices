package com.ms.stock_control_api.controller.v1;


import com.ms.stock_control_api.dtos.v1.watch.AllWatchsRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchResponseDTO;
import com.ms.stock_control_api.service.WatchService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/watch")
public class WatchController {

    @Autowired
    private WatchService watchService;

    @PostMapping("/")
    public ResponseEntity<WatchResponseDTO> saveWatch(@Valid @RequestBody WatchRequestDTO data){
        var watch = watchService.saveWatch(data);
        return ResponseEntity.ok(watch);
    }


    @GetMapping("")
    public ResponseEntity<AllWatchsRequestDTO> getAllWatches(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "12") Integer pageSize,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String movimentType,
            @RequestParam(required = false) String boxMaterial,
            @RequestParam(required = false) String glassType,
            @RequestParam(required = false) Integer minResistance,
            @RequestParam(required = false) Integer maxResistance,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Float minDiameter,
            @RequestParam(required = false) Float maxDiameter,
            @RequestParam(defaultValue = "newest") String orderBy
            ){
        var watches = watchService.getAll(pageNumber, pageSize, brand, movimentType, boxMaterial, glassType, minResistance, maxResistance, minPrice, maxPrice, minDiameter, maxDiameter, orderBy);
        return ResponseEntity.ok(watches);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WatchResponseDTO> getById(@PathVariable("id") UUID id){
       WatchResponseDTO watch = watchService.getById(id);
       return new ResponseEntity<>(watch, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id){
        watchService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<WatchResponseDTO> update(@PathVariable("id") UUID id,@Valid @RequestBody WatchRequestDTO data){
        var watch = watchService.update(id, data);
        return ResponseEntity.ok(watch);
    }

}
