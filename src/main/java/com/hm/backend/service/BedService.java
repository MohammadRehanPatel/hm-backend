package com.hm.backend.service;


import com.hm.backend.entity.Bed;
import com.hm.backend.repository.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedService {
    @Autowired
    private BedRepository bedRepository;

    public List<Bed> getAvailableBeds() {
        return bedRepository.findByAvailability(true);
    }

    public Bed getBedById(Long bedId){
        return bedRepository.findById(bedId).orElse(null);
    }

    public Bed registerBed(Bed bed) {
        return bedRepository.save(bed);
    }
}
