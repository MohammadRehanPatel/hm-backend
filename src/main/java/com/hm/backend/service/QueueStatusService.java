package com.hm.backend.service;

import com.hm.backend.entity.Doctor;
import com.hm.backend.entity.Queue;
import com.hm.backend.entity.QueueStatus;
import com.hm.backend.repository.DoctorRepository;
import com.hm.backend.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QueueStatusService {

    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    public List<QueueStatus> getRealtimeQueueStatus() {
        List<Queue> queues = queueRepository.findAll();
        List<QueueStatus> queueStatuses = new ArrayList<>();
        for (Queue queue : queues) {
            QueueStatus queueStatus = new QueueStatus(queue, calculateWaitTime(queue), isDoctorAvailable(queue.getDoctor()), queue.getQueuePosition());
            queueStatuses.add(queueStatus);
        }
        return queueStatuses;
    }

    private int calculateWaitTime(Queue queue) {
        int waitTime = 0;
        if (queue.getQueuePosition() > 1) {
            waitTime = queue.getQueuePosition() * 15; // assume 15 minutes per patient
        } else {
            Date currentTime = new Date();
            long timeDiff = queue.getAppointmentTime().getTime() - currentTime.getTime();
            waitTime = (int) (timeDiff / (60 * 1000)); // convert milliseconds to minutes
        }
        return waitTime;
    }

    private boolean isDoctorAvailable(Doctor doctor) {
        // check doctor availability logic here

        return doctor.isAvailable();

    }
}
