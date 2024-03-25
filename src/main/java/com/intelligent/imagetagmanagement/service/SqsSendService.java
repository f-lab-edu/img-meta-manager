package com.intelligent.imagetagmanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


@Service
@Slf4j
public class SqsSendService { // Service

    @Autowired
    private SqsSendService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    private final SqsClient sqsClient;

    @Value("${amazon.sqs.workflow.url}")
    private String QUEUE_URL;

    @Value("${amazon.sqs.workflow.name}")
    private String QUEUE_NAME;


    public void sendMessageToSQS(String message) {
        log.info("message : {}", message);
        log.info("queue url : {}", QUEUE_URL);
        sendMessageToSqsUrl(message, QUEUE_URL);

    }

    public void sendMessageToSqsUrl(String message, String queue_url) {
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queue_url)
                .messageBody(message)
                .build());
    }

}

