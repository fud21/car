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

    public void save(VehicleDto dto) {
        Vehicle vehicle = new Vehicle(dto.getPlateNumber(), dto.isRegistered());
        vehicleRepository.save(vehicle);
    }

    // 전체 조회
    public List<VehicleDto> findAll() {
        return vehicleRepository.findAll().stream()
                .map(v -> {
                    VehicleDto dto = new VehicleDto();
                    dto.setPlateNumber(v.getPlateNumber());
                    dto.setRegistered(v.isRegistered());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 단일 차량 조회
    public VehicleDto findByPlateNumber(String plateNumber) {
        Vehicle vehicle = vehicleRepository.findById(plateNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 존재하지 않습니다: " + plateNumber));

        VehicleDto dto = new VehicleDto();
        dto.setPlateNumber(vehicle.getPlateNumber());
        dto.setRegistered(vehicle.isRegistered());
        return dto;
    }

    // 차량 정보 수정
    public void update(String plateNumber, VehicleDto dto) {
        Vehicle vehicle = vehicleRepository.findById(plateNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 차량이 존재하지 않습니다: " + plateNumber));

        vehicle.setRegistered(dto.isRegistered());
        vehicleRepository.save(vehicle);
    }

    // 차량 삭제
    public void delete(String plateNumber) {
        if (!vehicleRepository.existsById(plateNumber)) {
            throw new IllegalArgumentException("해당 차량이 존재하지 않습니다: " + plateNumber);
        }
        vehicleRepository.deleteById(plateNumber);
    }

}
