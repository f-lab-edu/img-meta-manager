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
    static final class WORKFLOW_STATUS {
        public static final String COMPLETED = "completed";
        public static final String FAILED = "failed";
        public static final String IN_PROGRESS = "inProgress";

    }

    @Autowired
    private WorkQueueRepository workQueueRepository;

    private List<WorkQueueData> workQueueDataList;

    public void generateImageMetadata() {
        workQueueDataList = workQueueRepository.findForWorkflow();
        setWorkflowStatus(WORKFLOW_STATUS.IN_PROGRESS);

        try {
            // TODO :  image change or get metadata
            doWorkflow();

            setWorkflowStatus(WORKFLOW_STATUS.COMPLETED);
        } catch (Exception e) {
            log.error("work flow in error : {}", e.getMessage());
            setWorkflowStatus(WORKFLOW_STATUS.FAILED);
        }
    }

    public void setWorkflowStatus(String statusStr) {
        workQueueDataList.forEach(workQueueData -> {
            log.debug("Work Flow in progress uuid is : {}", workQueueData.getImageData().getUuid());
            workQueueData.setWork_status(statusStr);
            workQueueRepository.save(workQueueData);
        });
    }

    public void doWorkflow() throws Exception{
        workQueueDataList.forEach(workQueueData -> {
            log.debug("file location is : {}", workQueueData.getImageData().getFileLocation());
            log.debug("workflow in progress is : {}", workQueueData.getImageData().getFileLocation());
        });
    }


}
