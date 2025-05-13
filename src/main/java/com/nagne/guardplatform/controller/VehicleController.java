package com.nagne.guardplatform.controller;

import com.nagne.guardplatform.dto.VehicleDto;
import com.nagne.guardplatform.service.VehicleService;
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

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<VehicleDto>> findAll() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    // 단일 차량 조회
    @GetMapping("/{plateNumber}")
    public ResponseEntity<VehicleDto> findByPlateNumber(@PathVariable String plateNumber) {
        return ResponseEntity.ok(vehicleService.findByPlateNumber(plateNumber));
    }

    // 차량 정보 수정
    @PutMapping("/{plateNumber}")
    public ResponseEntity<String> update(@PathVariable String plateNumber, @RequestBody VehicleDto dto) {
        vehicleService.update(plateNumber, dto);
        return ResponseEntity.ok("차량 정보 수정 완료");
    }

    // 차량 삭제
    @DeleteMapping("/{plateNumber}")
    public ResponseEntity<String> delete(@PathVariable String plateNumber) {
        vehicleService.delete(plateNumber);
        return ResponseEntity.ok("차량 삭제 완료");
    }

}
