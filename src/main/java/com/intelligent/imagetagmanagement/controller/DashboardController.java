package com.intelligent.imagetagmanagement.controller;

import com.intelligent.imagetagmanagement.model.DashboardResponse;
import com.intelligent.imagetagmanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dashboard")
@RestController
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getAllImages() {
        return ResponseEntity
                .ok()
                .body(DashboardResponse
                        .builder()
                        .todayImageCount(dashboardService.getTodayUploadCount())
                        .totalSuccessfulWorkflowCount(dashboardService.getTotalSuccessWorkflowCount())
                        .totalFailedWorkflowCount(dashboardService.getTotalFailedWorkflowCount())
                        .todaySuccessfulWorkflowCount(dashboardService.getTodaySuccessWorkflowCount())
                        .todayFailedWorkflowCount(dashboardService.getTodayFailedWorkflowCount())
                        .totalImageCount(dashboardService.getTotalUploadCount())
                        .build());
    }
}
