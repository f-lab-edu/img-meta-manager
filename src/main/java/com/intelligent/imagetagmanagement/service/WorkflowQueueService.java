package com.intelligent.imagetagmanagement.service;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.WorkQueueData;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.util.ImageMetadataExtractorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class WorkflowQueueService {

    private final ImageRepository imageRepository;

    private final SqsSendService sqsSendService;
    private final ImageServices imageServices;

    @Autowired
    public WorkflowQueueService(ImageRepository imageRepository, SqsSendService sqsSendService, ImageServices imageServices) {
        this.imageRepository = imageRepository;
        this.sqsSendService = sqsSendService;
        this.imageServices = imageServices;
    }

    public void doWorkflow(String uuid) throws JpegProcessingException, IOException {
        log.debug("workflow uuid is : {}", uuid);
        Optional<ImageData> imageDataOptional = imageRepository.findById(uuid);
        ImageData imageData;
        if (imageDataOptional.isPresent()) {
            imageData = imageDataOptional.get();
        } else {
            throw new NoSuchElementException("image not found");
        }
        try {
            File file = new File(imageData.getFileLocation());
            if (!file.exists()) {
                throw new FileNotFoundException("file not found");
            }

            imageServices.saveAllMetadataListMap(ImageMetadataExtractorUtils.extractFileExifData(file), imageData);

            WorkQueueData.builder().imageData(imageData).work_status("completed").build();

        } catch (IOException e) {
            WorkQueueData.builder().imageData(imageData).work_status("failed").build();

            sqsSendService.sendMessageToSQS(uuid);

        }


    }
}
