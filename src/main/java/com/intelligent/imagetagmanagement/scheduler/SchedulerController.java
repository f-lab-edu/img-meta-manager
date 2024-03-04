package com.intelligent.imagetagmanagement.scheduler;

import com.intelligent.imagetagmanagement.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @Scheduled(cron = "0 * * * * *")
    public void workflowImage () {
        // TODO : cron workflow
        schedulerService.generateImageMetadata();

    }
}
