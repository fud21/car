package com.nagne.guardplatform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    private String plateNumber; // 차량 번호

    private boolean registered; // 등록 여부
}
