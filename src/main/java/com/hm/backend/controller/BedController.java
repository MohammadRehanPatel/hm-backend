package com.hm.backend.controller;

import com.hm.backend.entity.Bed;
import com.hm.backend.service.BedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds")
@Tag(name = "Bed APIs")
public class BedController {
    @Autowired
    private BedService bedService;

    @PostMapping()
    public Bed registerBed(@RequestBody Bed bed) {
        return bedService.registerBed(bed);
    }

    @GetMapping()
    public List<Bed> getAvailableBeds() {
        return bedService.getAvailableBeds();
    }
}
