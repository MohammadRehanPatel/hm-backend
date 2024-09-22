package com.hm.backend.controller;

import com.hm.backend.entity.Bed;
import com.hm.backend.entity.BedAllocation;
import com.hm.backend.service.BedAllocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Bed Allocation APIs")
public class BedAllocationController {
    @Autowired
    private BedAllocationService bedAllocationService;

    @PostMapping("/bed-allocation")
    public BedAllocation allocateBed(@RequestBody BedAllocation bedAllocation) {
        System.out.println(bedAllocation.getBed().isAvailability());
        return bedAllocationService.allocateBed(bedAllocation.getPatient().getId(), bedAllocation.getBed().getId());
    }

    @GetMapping("/bed-availability")
    public List<Bed> getBedAvailability() {
        return bedAllocationService.getBedAvailability();
    }
}
