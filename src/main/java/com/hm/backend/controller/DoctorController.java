package com.hm.backend.controller;
import com.hm.backend.entity.Doctor;
import com.hm.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

//    @GetMapping("/type/{doctorTypeId}")
//    public ResponseEntity<List<Doctor>> getDoctorsByType(@PathVariable Long doctorTypeId) {
//        DoctorType doctorType = new DoctorType();
//        doctorType.setId(doctorTypeId);
//        List<Doctor> doctors = doctorService.getDoctorsByType(doctorType);
//        return new ResponseEntity<>(doctors, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

//    @GetMapping("/available/{doctorTypeId}")
//    public ResponseEntity<Doctor> getAvailableDoctorByType(@PathVariable Long doctorTypeId) {
//        DoctorType doctorType = new DoctorType();
//        doctorType.setId(doctorTypeId);
//        Doctor doctor = doctorService.getAvailableDoctorByType(doctorType);
//        return new ResponseEntity<>(doctor, HttpStatus.OK);
//    }

//    @PatchMapping("/{id}/availability")
//    public ResponseEntity<Doctor> updateDoctorAvailability(@PathVariable Long id, @RequestBody boolean availability) {
//        Doctor doctor = doctorService.getDoctorById(id);
//        if (doctor != null) {
//            doctorService.updateDoctorAvailability(doctor, availability);
//            return new ResponseEntity<>(doctor, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

    @PostMapping("")
    public Doctor registerDoctor(@RequestBody Doctor doctor) {
        return doctorService.registerDoctor(doctor);
    }

    @GetMapping("/{specialization}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);




    }
}
