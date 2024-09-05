package com.hm.backend.controller;

import com.hm.backend.entity.QueueStatus;
import com.hm.backend.service.QueueStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/queue/status")
public class QueueStatusController {
    @Autowired
    private QueueStatusService queueStatusService;

    @GetMapping("/realtime")
    public ResponseEntity<List<QueueStatus>> getRealtimeQueueStatus() {
        return ResponseEntity.ok(queueStatusService.getRealtimeQueueStatus());
    }
}
