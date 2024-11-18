package com.hm.backend.controller;

import com.hm.backend.entity.Doctor;
import com.hm.backend.entity.QueueToken;
import com.hm.backend.service.PatientNotificationService;
import com.hm.backend.service.QueueManagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@Tag(name = "Queue Management APIs")
public class QueueManagementController {
    @Autowired
    private QueueManagementService queueManagementService;
    @Autowired
    private PatientNotificationService patientNotificationService;

    @PostMapping("/queue-token/{specialization}")
    public QueueToken allocateToken(@PathVariable String specialization, @RequestParam Long patientId) {
        return queueManagementService.allocateTokenBySpecialization(specialization, patientId);
    }

    @GetMapping("/queue-status/{specialization}")
    public List<QueueToken> getQueueStatusBySpecialization(@PathVariable String specialization) {
        return queueManagementService.getQueueStatusBySpecialization(specialization);
    }

    @GetMapping("/queue-status")
    public List<QueueToken> getQueueStatus() {
        return queueManagementService.getQueueStatus();
    }

    @DeleteMapping("/queue/{queueTokenId}")
    public ResponseEntity<String> removePatientFromQueue(@PathVariable Long queueTokenId) {
        QueueToken queueToken = queueManagementService.findByTokenId(queueTokenId);
        if (queueToken != null) {
            queueManagementService.markPatientAsCheckedUpAndRemove(queueToken.getId());
            return ResponseEntity.ok("Patient removed from queue successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cancel-token/{queueTokenId}")
    public ResponseEntity<String> cancelToken(@PathVariable Long queueTokenId) {
        QueueToken queueToken = queueManagementService.findByTokenId(queueTokenId);
        if (queueToken != null) {
            queueManagementService.cancelToken(queueToken.getId());
            return ResponseEntity.ok("Token cancelled successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/doctor-availability/{doctorId}")
    public ResponseEntity<String> getDoctorAvailability(@PathVariable Long doctorId) {
        Doctor doctor = queueManagementService.getDoctorById(doctorId);
        if (doctor != null) {
            return ResponseEntity.ok("Doctor is available: " + doctor.isAvailability());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/notify-patient/{queueTokenId}")
    public ResponseEntity<String> notifyPatient(@PathVariable Long queueTokenId) {
        QueueToken queueToken = queueManagementService.findByTokenId(queueTokenId);
        if (queueToken != null) {
            queueManagementService.sendNotification(queueToken);
            return ResponseEntity.ok("Patient notified successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }




//    @GetMapping("/waiting-time/{queueTokenId}")
//    public ResponseEntity<String> getWaitingTime(@PathVariable Long queueTokenId) {
//        QueueToken queueToken = queueManagementService.findByTokenId(queueTokenId);
//        if (queueToken != null) {
//            return ResponseEntity.ok("Waiting time: " + queueManagementService.ge(queueToken));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


}
