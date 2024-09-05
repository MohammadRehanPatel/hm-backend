package com.hm.backend.controller;

import com.hm.backend.entity.Patient;
import com.hm.backend.entity.Queue;
import com.hm.backend.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveQueue(@RequestBody Patient patient) {
        queueService.reserveQueue(patient);
        return ResponseEntity.ok("Queue reserved successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<List<Queue>> getQueueStatus() {
        return ResponseEntity.ok(queueService.getQueueStatus());
    }
}
