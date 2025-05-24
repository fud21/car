package com.nagne.carguardplatform.service;

import com.nagne.carguardplatform.dto.VehicleDto;
import com.nagne.carguardplatform.entity.Vehicle;
import com.nagne.carguardplatform.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    // 차량 저장
    public void save(VehicleDto dto) {
        Vehicle vehicle = new Vehicle(
                dto.getPlateNumber(),
                dto.isRegistered(),
                dto.getLocation(),
                dto.getLastSeen()
        );
        vehicleRepository.save(vehicle);
    }

    // 등록 차량만 조회
    public List<VehicleDto> findRegisteredVehicles() {
        return vehicleRepository.findByRegisteredTrue().stream()
                .map(v -> new VehicleDto(
                        v.getPlateNumber(),
                        v.isRegistered(),
                        v.getLocation(),
                        v.getLastSeen()
                ))
                .collect(Collectors.toList());
    }

    // 전체 출입 차량 기록
    public List<VehicleDto> findAllVehicleHistory() {
        return vehicleRepository.findAll().stream()
                .map(v -> new VehicleDto(
                        v.getPlateNumber(),
                        v.isRegistered(),
                        v.getLocation(),
                        v.getLastSeen()
                ))
                .collect(Collectors.toList());
    }

    // 차량 삭제
    public void delete(String plateNumber) {
        if (!vehicleRepository.existsById(plateNumber)) {
            throw new IllegalArgumentException("해당 차량이 존재하지 않습니다: " + plateNumber);
        }
        vehicleRepository.deleteById(plateNumber);
    }
}
