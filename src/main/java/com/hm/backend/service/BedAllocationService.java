package com.hm.backend.service;


import com.hm.backend.entity.Bed;
import com.hm.backend.entity.BedAllocation;
import com.hm.backend.entity.Patient;
import com.hm.backend.repository.BedAllocationRepository;
import com.hm.backend.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedAllocationService {
    @Autowired
    private BedAllocationRepository bedAllocationRepository;

    @Autowired
    private BedRepository bedRepository;
    @Autowired
    private BedService bedService;
    @Autowired
    private PatientService patientService;

//    public BedAllocation allocateBed(Patient patient, Bed bed) {
//        if (bed.isAvailability()) {
////        if (true) {
//            BedAllocation bedAllocation = new BedAllocation(bed, patient, true);
//
//            bedAllocationRepository.save(bedAllocation);
//
//            updateBedAvailability(bed);
//
//            return bedAllocation;
//        } else {
//            return null;
//        }
//    }
    public BedAllocation allocateBed(Long patientId, Long bedId) {

        Patient patient = patientService.getPatientById(patientId);
        Bed bed = bedService.getBedById(bedId);

        if (bed.isAvailability() && !isBedAlreadyAllocated(bed)) {
            BedAllocation bedAllocation = new BedAllocation(bed, patient, true);

            bedAllocationRepository.save(bedAllocation);

            updateBedAvailability(bed,false);

            return bedAllocation;
        } else {
            return null;
        }
    }

    public boolean isBedAlreadyAllocated(Bed bed) {
        return bedAllocationRepository.existsByBed(bed);
    }

    public void updateBedAvailability(Bed bed, boolean availability) {
        bed.setAvailability(availability);
        bedRepository.save(bed);
    }

    public List<Bed> getBedAvailability() {
        return bedService.getAvailableBeds();
    }
}
