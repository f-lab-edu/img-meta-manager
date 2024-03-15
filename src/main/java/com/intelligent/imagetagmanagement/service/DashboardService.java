package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.WorkQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    WorkQueueRepository workQueueRepository;

    @Autowired
    ImageRepository imageRepository;


    public long getTotalFailedWorkflowCount() {
        return workQueueRepository.getTotalFailedWorkflowCount();
    }
    public long getTotalSuccessWorkflowCount() {
        return workQueueRepository.getTotalSuccessWorkflowCount();
    }
    public long getTodayFailedWorkflowCount(){
        return workQueueRepository.getTodayFailedWorkflowCount();
    }
    public long getTodaySuccessWorkflowCount() {
        return workQueueRepository.getTodaySuccessWorkflowCount();
    }

    public long getTotalUploadCount() {
        return imageRepository.getTotalUploadCount();
    }
    public long getTodayUploadCount() {
        return imageRepository.getTodayUploadCount();
    }

}
