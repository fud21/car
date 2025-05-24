package com.nagne.carguardplatform.controller;

import com.nagne.carguardplatform.dto.VehicleDto;
import com.nagne.carguardplatform.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody VehicleDto dto) {
        vehicleService.save(dto);
        return ResponseEntity.ok("차량 저장 완료");
    }

    @DeleteMapping("/{plateNumber}")
    public ResponseEntity<String> delete(@PathVariable String plateNumber) {
        vehicleService.delete(plateNumber);
        return ResponseEntity.ok("차량 삭제 완료");
    }

    @GetMapping("/registered")
    public ResponseEntity<List<VehicleDto>> findRegisteredVehicles() {
        return ResponseEntity.ok(vehicleService.findRegisteredVehicles());
    }

    @GetMapping("/history")
    public ResponseEntity<List<VehicleDto>> findAllVehicleHistory() {
        return ResponseEntity.ok(vehicleService.findAllVehicleHistory());
    }
}
