package com.nagne.carguardplatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VehicleDto {
    private String plateNumber;
    private boolean registered;
    private String location;
    private LocalDateTime lastSeen;

    // ✅ 전체 필드용 생성자 추가
    public VehicleDto(String plateNumber, boolean registered, String location, LocalDateTime lastSeen) {
        this.plateNumber = plateNumber;
        this.registered = registered;
        this.location = location;
        this.lastSeen = lastSeen;
    }
}

