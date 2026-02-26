package com.ms.stock_control_api.controller.v1;


import com.ms.stock_control_api.dtos.v1.watch.AllWatchsRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchRequestDTO;
import com.ms.stock_control_api.dtos.v1.watch.WatchResponseDTO;
import com.ms.stock_control_api.service.WatchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "60") Integer pageSize,
            @RequestParam(required = false) String brand
            ){
        var watches = watchService.getAll(pageNumber, pageSize, brand);
        return ResponseEntity.ok(watches);
    }

}
