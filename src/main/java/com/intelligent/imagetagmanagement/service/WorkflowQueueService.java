package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.util.SqsSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkflowQueueService {

    public void doWorkflow(String uuid) {
        try {
            log.debug("workflow uuid is : {}", uuid);
        } catch (Exception e) {
            SqsSendUtils.sendMessageToSQS(uuid);
        }

    }
}
