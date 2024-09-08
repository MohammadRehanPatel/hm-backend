package com.hm.backend.repository;

import com.hm.backend.entity.Bed;
import com.hm.backend.entity.BedAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedAllocationRepository  extends JpaRepository<BedAllocation,Long> {
    List<BedAllocation> findByPatientId(Long patientId);

    boolean existsByBed(Bed bed);
}
