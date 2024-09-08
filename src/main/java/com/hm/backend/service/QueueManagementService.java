package com.hm.backend.service;

import com.hm.backend.entity.Doctor;
import com.hm.backend.entity.Patient;
import com.hm.backend.entity.QueueToken;
import com.hm.backend.repository.QueueTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QueueManagementService {
    @Autowired
    private QueueTokenRepository queueTokenRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;

    private int tokenNumber = 1;
    private Map<Doctor, Integer> waitingTimeMap = new HashMap<>();

    public QueueToken allocateToken(Long patientId, Long doctorId) {
        Patient patient = patientService.getPatientById(patientId);
        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor.isAvailability()) {
            double waitingTime = calculateWaitingTime(doctor);
            QueueToken queueToken = new QueueToken(patient, doctor, tokenNumber, waitingTime, doctor.getSpecialization());
            queueTokenRepository.save(queueToken);
            updateWaitingTime(queueToken);
            tokenNumber++;
            return queueToken;
        } else {
            return null;
        }
    }

    public void updateWaitingTime(QueueToken queueToken) {
        Doctor doctor = queueToken.getDoctor();
        int waitingTime = calculateWaitingTime(doctor);
        waitingTimeMap.put(doctor, waitingTime);
    }

    private int calculateWaitingTime(Doctor doctor) {
        List<QueueToken> queueTokens = queueTokenRepository.findByDoctor(doctor);
        int totalPatients = 0;
        for (QueueToken queueToken : queueTokens) {
            if (!queueToken.isCheckedUp()) {
                totalPatients++;
            }
        }
        return totalPatients * 15;
    }

    public void markPatientAsCheckedUpAndRemove(Long queueTokenId) {
        QueueToken queueToken = queueTokenRepository.findById(queueTokenId).orElse(null);
        if (queueToken != null) {
            queueToken.setCheckedUp(true);
            queueTokenRepository.save(queueToken);
            removePatientFromQueue(queueTokenId);
        }
    }

    public void removePatientFromQueue(Long queueTokenId) {
        QueueToken queueToken = queueTokenRepository.findById(queueTokenId).orElse(null);
        if (queueToken != null && queueToken.isCheckedUp()) {
            queueTokenRepository.delete(queueToken);
            updateWaitingTime(queueToken);
        }
    }

    public QueueToken findByTokenId(Long id){
        return queueTokenRepository.findById(id).orElse(null);
    }

    public List<QueueToken> getQueueStatus() {
        return queueTokenRepository.findAll();
    }
}
