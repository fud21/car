package com.nagne.carguardplatform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    private String plateNumber; // 차량 번호
    private boolean registered; // 등록 여부
    private String location; // 마지막 감지 위치
    private LocalDateTime lastSeen; // 마지막 감지 시간 출력

}

