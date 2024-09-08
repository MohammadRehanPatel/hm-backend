package com.hm.backend.controller;

import com.hm.backend.entity.QueueToken;
import com.hm.backend.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QueueManagementController {
    @Autowired
    private QueueManagementService queueManagementService;

    @PostMapping("/queue-token")
    public QueueToken allocateToken(@RequestBody QueueToken queueToken) {
        return queueManagementService.allocateToken(queueToken.getPatient().getId(), queueToken.getDoctor().getId());
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
}
