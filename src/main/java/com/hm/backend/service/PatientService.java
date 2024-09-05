package com.hm.backend.service;

import com.hm.backend.entity.Patient;
import com.hm.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void registerPatient(Patient patient) {
        patientRepository.save(patient);
    }

}
