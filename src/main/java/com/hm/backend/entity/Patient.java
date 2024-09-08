package com.hm.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
//Technological solution as per queuing models in OPDs/availability of beds/admission of patients would be one area. Study of dispensation of various types of medicines/consumables and Inventory management modules at hospital level are key areas requiring support. NIC has already developed some modules but their implementation in Delhi is yet to be started. A hospital based solution is ideal which can be integrated with city wide module may be required.

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




    public Patient(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }
}
