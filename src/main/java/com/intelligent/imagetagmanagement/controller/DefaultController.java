package com.intelligent.imagetagmanagement.controller;

import com.intelligent.imagetagmanagement.model.DashboardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class DefaultController {

    @GetMapping
    public ResponseEntity<DashboardResponse> heathCheck() {
        return ResponseEntity
                .ok()
                .build();
    }
}
