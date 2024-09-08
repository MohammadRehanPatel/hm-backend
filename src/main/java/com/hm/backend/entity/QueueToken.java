package com.hm.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private Integer tokenNumber;

    @Column(nullable = false)
    private Double waitingTime;

    @Column(nullable = false)
    private String doctorSpecialization;
    @Column
    private boolean checkedUp;

    public QueueToken(Patient patient, Doctor doctor, Integer tokenNumber, Double waitingTime, String doctorSpecialization) {
        this.patient = patient;
        this.doctor = doctor;
        this.tokenNumber = tokenNumber;
        this.waitingTime = waitingTime;
        this.doctorSpecialization = doctorSpecialization;
    }

    // getters and setters
}
