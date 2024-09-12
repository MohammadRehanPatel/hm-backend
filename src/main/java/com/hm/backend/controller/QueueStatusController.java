package com.hm.backend.controller;

import com.hm.backend.entity.QueueToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/queue/status")
public class QueueStatusController {
//    @Autowired
//    private QueueStatusService queueStatusService;

    @GetMapping("/realtime")
    public ResponseEntity<List<QueueToken>> getRealtimeQueueStatus() {
        return ResponseEntity.ok(null);
    }
}
