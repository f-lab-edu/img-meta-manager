package com.intelligent.imagetagmanagement.listener;

import com.intelligent.imagetagmanagement.service.WorkflowQueueService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SqsMessageListener {
    @Autowired
    WorkflowQueueService workflowQueueService;

    @SqsListener("${amazon.sqs.workflow.name}")
    public void messageListener(String message) {
        log.info("message : {}", message);

        workflowQueueService.doWorkflow(message);

    }
}
