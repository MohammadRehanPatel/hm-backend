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
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "appointment_time")
    private Date appointmentTime;

    public Patient(String name, String contactNumber, String email, Date appointmentTime) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.appointmentTime = appointmentTime;
    }
}
