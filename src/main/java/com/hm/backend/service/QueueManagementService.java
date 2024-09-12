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
    @Autowired
    PatientNotificationService notificationService;
    private int tokenNumber = 1;
    private Map<Doctor, Integer> waitingTimeMap = new HashMap<>();

//    public QueueToken allocateToken(Long patientId, Long doctorId) {
//        Patient patient = patientService.getPatientById(patientId);
//        Doctor doctor = doctorService.getDoctorById(doctorId);
//
//        if (doctor.isAvailability()) {
//            double waitingTime = calculateWaitingTime(doctor);
//            QueueToken queueToken = new QueueToken(patient, doctor, tokenNumber, waitingTime, doctor.getSpecialization());
//            queueTokenRepository.save(queueToken);
//            updateWaitingTime(queueToken);
//            tokenNumber++;
//            return queueToken;
//        } else {
//            return null;
//        }
//    }
    private Map<String, Integer> tokenNumberMap = new HashMap<>();

    public QueueToken allocateTokenBySpecialization(String specialization, Long patientId) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        for (Doctor doctor : doctors) {
            if (doctor.isAvailability()) {
                double waitingTime = calculateWaitingTime(doctor);
                Patient patient = patientService.getPatientById(patientId);
                int tokenNumber = getTokenNumber(specialization);
                QueueToken queueToken = new QueueToken(patient, doctor, tokenNumber, waitingTime, doctor.getSpecialization());
                queueTokenRepository.save(queueToken);
                updateWaitingTime(queueToken);
                incrementTokenNumber(specialization);
                return queueToken;
            }
        }
        return null;
    }

    public List<QueueToken> getQueueStatusBySpecialization(String specialization) {
        return queueTokenRepository.findByDoctorSpecialization(specialization);
    }
    private int getTokenNumber(String specialization) {
        if (tokenNumberMap.containsKey(specialization)) {
            return tokenNumberMap.get(specialization);
        } else {
            tokenNumberMap.put(specialization, 1);
            return 1;
        }
    }

    private void incrementTokenNumber(String specialization) {
        int tokenNumber = tokenNumberMap.get(specialization);
        tokenNumberMap.put(specialization, tokenNumber + 1);
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

    public void createQueueBySpecialization(String specialization) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        for (Doctor doctor : doctors) {
            List<QueueToken> doctorQueueTokens = queueTokenRepository.findByDoctor(doctor);
            if (doctorQueueTokens.isEmpty()) {
                QueueToken queueToken = new QueueToken( null, doctor, 1, (double) 0, doctor.getSpecialization());
                queueTokenRepository.save(queueToken);
            } else {
                QueueToken lastQueueToken = doctorQueueTokens.get(doctorQueueTokens.size() - 1);
                int newTokenNumber = lastQueueToken.getTokenNumber() + 1;
                QueueToken newQueueToken = new QueueToken(null, doctor, newTokenNumber, (double) 0, doctor.getSpecialization());
                queueTokenRepository.save(newQueueToken);
            }
        }
    }

    public void addPatientToQueueBySpecialization(String specialization, Long patientId) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        for (Doctor doctor : doctors) {
            List<QueueToken> doctorQueueTokens = queueTokenRepository.findByDoctor(doctor);
            Patient patient = null;
            if (!doctorQueueTokens.isEmpty()) {
                QueueToken lastQueueToken = doctorQueueTokens.get(doctorQueueTokens.size() - 1);
                int newTokenNumber = lastQueueToken.getTokenNumber() + 1;
                patient = patientService.getPatientById(patientId);
                QueueToken newQueueToken = new QueueToken(patient, doctor, newTokenNumber, (double) 0, doctor.getSpecialization());
                queueTokenRepository.save(newQueueToken);
            } else {
                QueueToken queueToken = new QueueToken(patient, doctor, 1, (double) 0, doctor.getSpecialization());
                queueTokenRepository.save(queueToken);
            }
        }
    }


    public QueueToken findByTokenId(Long id){
        return queueTokenRepository.findById(id).orElse(null);
    }

    public List<QueueToken> getQueueStatus() {
        return queueTokenRepository.findAll();
    }
    public void cancelToken(Long queueTokenId) {
        QueueToken queueToken = queueTokenRepository.findById(queueTokenId).orElseThrow(() -> new RuntimeException("Queue token not found"));
        queueTokenRepository.deleteById(queueTokenId);
    }
    public Doctor getDoctorById(Long doctorId) {
        return doctorService.getDoctorById(doctorId);
    }

    public void sendNotification(QueueToken queueToken) {
        // Send SMS or email notification to the patient
        String patientName = queueToken.getPatient().getName();
        String doctorName = queueToken.getDoctor().getName();
        String specialization = queueToken.getDoctor().getSpecialization();
        String waitingTime = String.valueOf(queueToken.getWaitingTime());
        String message = "Hello " + patientName + ",\n\nYou are next in line to see " + doctorName + " (" + specialization + ").\nYour estimated waiting time is " + waitingTime + " minutes.\n\nThank you for choosing our hospital.";

        notificationService.sendEmail(queueToken.getPatient(), "Appointment Reminder", message);
        notificationService.sendSms(queueToken.getPatient().getContactNumber(), "Hello " + patientName +
                ", you are next in line to see " + doctorName + " (" + specialization + "). Your estimated waiting time is " + waitingTime + " minutes. Thank you for choosing our hospital.");
    }
}
