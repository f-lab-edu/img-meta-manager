package com.intelligent.imagetagmanagement.listener;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.intelligent.imagetagmanagement.service.WorkflowQueueService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SqsMessageListener {
    @Autowired
    WorkflowQueueService workflowQueueService;

    @SqsListener("${amazon.sqs.workflow.name}")
    public void messageListener(String message) throws JpegProcessingException {
        log.info("message : {}", message);

        try {
        workflowQueueService.doWorkflow(message);

        } catch (IOException e){
            log.error(e.getMessage());
        }

    }
}
