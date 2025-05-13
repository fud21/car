package com.nagne.guardplatform.repository;

import com.nagne.guardplatform.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
