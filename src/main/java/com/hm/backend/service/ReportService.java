package com.hm.backend.service;

import com.hm.backend.entity.Report;
import com.hm.backend.repository.ReportRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
//    save
    @CachePut(value = "reports",key = "#report.id")
    public Report createReport(Report report){
        return reportRepository.save(report);
    }
//    Get All

//    @Cacheable(value = "reports",)
    public List<Report > getAllReports(){
        return reportRepository.findAll();
    }

//    get by id

    @Cacheable(value = "reports",key = "#id")
    public Report getReportById(long id){
        return reportRepository.findById(id).orElseThrow(()-> new RuntimeException("Report Id Not Found"));
    }

//    Update
    public Report updateReport(Report report, long id){
        Report r = reportRepository.findById(id).orElseThrow(()-> new RuntimeException("Report Id Not Found"));
        r.setPatientName(report.getPatientName());
        r.setDetails(report.getDetails());
        r.setReportDate(report.getReportDate());
        return reportRepository.save(r);

    }

//    Delete

    @CacheEvict(value = "reports",key = "noteId")
    public void deleteReport(long id){
        Report r = reportRepository.findById(id).orElseThrow(()-> new RuntimeException("Report Id Not Found"));
        reportRepository.delete(r);
    }


}
