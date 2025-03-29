package com.example.CaloriesTrackingService.controllers;

import com.example.CaloriesTrackingService.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportControllerV1 {
    private final ReportService reportService;

    @GetMapping("meals_today/{date}/{id}")
    private ResponseEntity getReportForDay(@PathVariable LocalDate date, @PathVariable long id) {
        return reportService.getReportForDay(id ,date);
    }

    @GetMapping("check_intake/{date}/{id}")
    private ResponseEntity getDailyAllowanceIsFulfilled(@PathVariable LocalDate date, @PathVariable long id) {
        return reportService.getDailyAllowanceIsFulfilled(id, date);
    }

    @GetMapping("history/{id}")
    private ResponseEntity getHistoryByDays( @PathVariable long id) {
        return reportService.getHistoryByDays(id);
    }
}
