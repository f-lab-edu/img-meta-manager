package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.model.WorkQueueData;
import com.intelligent.imagetagmanagement.repository.WorkQueueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private WorkQueueRepository workQueueRepository;
    public void generateImageMetadata(){
        List<WorkQueueData> workQueueDataList = workQueueRepository.findForWorkflow();

        workQueueDataList.forEach(workQueueData -> {
            log.debug(workQueueData.getWork_status());
            workQueueData.setWork_status("inProgress");
            workQueueRepository.save(workQueueData);
        });

        try {
            // TODO :  image change or get metadata
            workQueueDataList.forEach(workQueueData -> {
                log.debug("Work Flow in progress uuid is : {}", workQueueData.getImageData().getUuid());
                workQueueData.setWork_status("completed");
                workQueueRepository.save(workQueueData);
            });

        } catch (Exception e) {
            workQueueDataList.forEach(workQueueData -> {
                log.debug(workQueueData.getWork_status());
                workQueueData.setWork_status("filed");
                workQueueRepository.save(workQueueData);
            });
        }



    }
}
