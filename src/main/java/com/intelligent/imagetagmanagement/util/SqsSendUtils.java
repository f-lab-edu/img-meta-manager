package com.intelligent.imagetagmanagement.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


@Profile("local")
public class SqsSendUtils {

    @Value("${amazon.sqs.workflow.URL}")
    private static String QUEUE_URL;

    @Value("${amazon.sqs.workflow.name}")
    private static String QUEUE_NAME;


    public static void sendMessageToSQS(String message) {
        sendMessageToSqsUrl(message, QUEUE_URL, QUEUE_NAME);

    }

    public static void sendMessageToSqsUrl(String message, String queue_url, String queue_name) {
        try (SqsClient sqsClient = SqsClient.builder().region(Region.AP_NORTHEAST_2).build()) {
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queue_url)
                    .messageGroupId(queue_name)
                    .messageDeduplicationId(queue_name)
                    .messageBody(message)
                    .build();
            sqsClient.sendMessage(sendMsgRequest);
        }
    }
}
