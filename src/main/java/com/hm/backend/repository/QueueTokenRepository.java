package com.hm.backend.repository;

import com.hm.backend.entity.Doctor;
import com.hm.backend.entity.QueueToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueTokenRepository extends JpaRepository<QueueToken,Long> {
    List<QueueToken> findByDoctorSpecialization(String specialization);

    List<QueueToken> findByDoctor(Doctor doctor);
//    List<QueueToken> findByDoctorSpecialization(String specialization);
}
