package com.intelligent.imagetagmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


@Service
public class SqsSendService { // Service

    @Autowired
    private SqsSendService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    private final SqsClient sqsClient;

    @Value("${amazon.sqs.workflow.url}")
    private static String QUEUE_URL;

    @Value("${amazon.sqs.workflow.name}")
    private static String QUEUE_NAME;


    public void sendMessageToSQS(String message) {
        sendMessageToSqsUrl(message, QUEUE_URL, QUEUE_NAME);

    }

    public void sendMessageToSqsUrl(String message, String queue_url, String queue_name) {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queue_url)
                    .messageGroupId(queue_name)
                    .messageDeduplicationId(queue_name)
                    .messageBody(message)
                    .build());
    }

}

