package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.WorkQueueData;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.WorkQueueRepository;
import com.intelligent.imagetagmanagement.util.SqsSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class WorkflowQueueService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private WorkQueueRepository workQueueRepository;

    public void doWorkflow(String uuid) {
        log.debug("workflow uuid is : {}", uuid);
        Optional<ImageData> imageDataOptional = imageRepository.findById(uuid);
        ImageData imageData;
        if (imageDataOptional.isPresent()) {
            imageData = imageDataOptional.get();
        } else {
            throw new NoSuchElementException("image not found");
        }
        try {
            WorkQueueData.builder().imageData(imageData).work_status("completed").build();

        } catch (Exception e) {
            WorkQueueData.builder().imageData(imageData).work_status("failed").build();

            SqsSendUtils.sendMessageToSQS(uuid);
        }

    }
}
