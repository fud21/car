package com.nagne.guardplatform.service;

import com.nagne.guardplatform.dto.WarningDto;
import com.nagne.guardplatform.entity.Warning;
import com.nagne.guardplatform.entity.Vehicle;
import com.nagne.guardplatform.repository.VehicleRepository;
import com.nagne.guardplatform.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.nagne.guardplatform.util.WarningStreamManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarningService {

    private final WarningRepository warningRepository;
    private final VehicleRepository vehicleRepository;
    public void issueWarning(WarningDto dto) {
        // 등록된 차량이면 경고 기록 X
        if (vehicleRepository.findById(dto.getPlateNumber())
                .map(Vehicle::isRegistered)
                .orElse(false)) {
            throw new IllegalArgumentException("등록된 차량은 경고 대상이 아닙니다.");
        }

        // 미등록 차량일 경우만 경고 저장
        Warning warning = new Warning();
        warning.setPlateNumber(dto.getPlateNumber());
        warning.setLocation(dto.getLocation());
        warning.setTimestamp(LocalDateTime.now());
        warningRepository.save(warning);

        // ✅ 실시간 알림 전송
        WarningStreamManager.send("🚨 " + dto.getPlateNumber() + " 경고 발생 @ " + dto.getLocation());

    }
    
    public List<WarningDto> findAllWarnings() {
        return warningRepository.findAll().stream()
                .map(w -> {
                    WarningDto dto = new WarningDto();
                    dto.setPlateNumber(w.getPlateNumber());
                    dto.setLocation(w.getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<WarningDto> filterWarnings(String plateNumber, String location, LocalDate date) {
        return warningRepository.filterWarnings(plateNumber, location, date).stream()
                .map(w -> {
                    WarningDto dto = new WarningDto();
                    dto.setId(w.getId());
                    dto.setPlateNumber(w.getPlateNumber());
                    dto.setLocation(w.getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}



