package com.hm.backend.controller;

import com.hm.backend.entity.Report;
import com.hm.backend.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")

public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    create
    @PostMapping()
    public ResponseEntity<Report> createReport(@RequestBody Report report){
        return new ResponseEntity<>(reportService.createReport(report), HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<Report>> getAllReports(){
        return new ResponseEntity<>(reportService.getAllReports(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable long id){
        return new ResponseEntity<>(reportService.getReportById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReportById(@RequestBody Report report,@PathVariable long id){
        return new ResponseEntity<>(reportService.updateReport(report,id),HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public void  deleteReportById(@PathVariable long id){
        reportService.deleteReport(id);
    }

}
