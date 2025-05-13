package com.nagne.carguardplatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WarningDto {
    private Long id;
    private String plateNumber;
    private String location;
    private String timestamp;
}