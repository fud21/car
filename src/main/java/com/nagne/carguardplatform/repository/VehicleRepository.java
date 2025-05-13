package com.nagne.carguardplatform.repository;

import com.nagne.carguardplatform.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
