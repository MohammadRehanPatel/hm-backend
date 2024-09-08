package com.hm.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private Integer bedNumber;

    @Column(nullable = false)
    private String bedType;

    private boolean availability;

    public Bed(Integer bedNumber, String bedType, boolean availability) {
        this.bedNumber = bedNumber;
        this.bedType = bedType;
        this.availability = availability;
    }



    // getters and setters
}
