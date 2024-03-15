package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.ImageMetaData;
import com.intelligent.imagetagmanagement.model.WorkQueueData;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.MetadataRepository;
import com.intelligent.imagetagmanagement.repository.WorkQueueRepository;
import com.intelligent.imagetagmanagement.util.ImageMetadataExtractorUtils;
import com.intelligent.imagetagmanagement.util.SqsSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class WorkflowQueueService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetadataRepository metadataRepository;

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
            File file = new File(imageData.getFileLocation());
            if (!file.exists()) {
                throw new FileNotFoundException("file not found");
            }

            List<ImageMetaData> inputImageMetadata = new ArrayList<>();
            ImageMetadataExtractorUtils.extractFileExifData(file).forEach((metaKey, metaValue) -> {
                inputImageMetadata.add(ImageMetaData.builder().key(metaKey).stringValue(metaValue).imageData(imageData).build());
            });

            metadataRepository.saveAll(inputImageMetadata);

            WorkQueueData.builder().imageData(imageData).work_status("completed").build();

        } catch (Exception e) {
            WorkQueueData.builder().imageData(imageData).work_status("failed").build();

            SqsSendUtils.sendMessageToSQS(uuid);
        }

    }
}
