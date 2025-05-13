package com.nagne.carguardplatform.repository;

import com.nagne.carguardplatform.entity.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WarningRepository extends JpaRepository<Warning, Long> {

    @Query("SELECT w FROM Warning w " +
            "WHERE (:plateNumber IS NULL OR w.plateNumber = :plateNumber) " +
            "AND (:location IS NULL OR w.location LIKE %:location%) " +
            "AND (:date IS NULL OR DATE(w.timestamp) = :date)")
    List<Warning> filterWarnings(String plateNumber, String location, LocalDate date);
}
