package com.hm.backend.service;

import com.hm.backend.entity.Doctor;
import com.hm.backend.entity.Patient;
import com.hm.backend.entity.Queue;
import com.hm.backend.repository.DoctorRepository;
import com.hm.backend.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QueueService {
    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    public void reserveQueue(Patient patient) {
        Doctor doctor = doctorRepository.findFirstByAvailableTrue();

        if (doctor != null) {
            Queue queue = new Queue(patient, doctor, new Date(), patient.getAppointmentTime(), 1);
            queueRepository.save(queue);
        } else {
            System.out.println("No available doctor found. Please try again later.");
        }
    }

    public List<Queue> getQueueStatus() {
        return queueRepository.findAll();
    }
}
