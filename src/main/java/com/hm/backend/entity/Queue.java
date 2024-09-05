package com.hm.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "queues")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "queue_time")
    private Date queueTime;

    @Column(name = "appointment_time")
    private Date appointmentTime;

    @Column(name = "queue_position")
    private int queuePosition;
    public Queue(Patient patient, Doctor doctor, Date queueTime, Date appointmentTime, int queuePosition) {
        this.patient = patient;
        this.doctor = doctor;
        this.queueTime = queueTime;
        this.appointmentTime = appointmentTime;
        this.queuePosition = queuePosition;
    }
}
