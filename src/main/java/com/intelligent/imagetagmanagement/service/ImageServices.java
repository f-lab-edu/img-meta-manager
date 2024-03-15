package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.ImageMetaData;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.MetadataRepository;
import com.intelligent.imagetagmanagement.util.SqsSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
public class ImageServices {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;


    public ImageData registerImage(MultipartFile multipartFile, ImageData imageData) throws IOException {
        ImageData resultImageData;
        // validation
        resultImageData = imageRepository.save(imageData);

        resultImageData = importImageAndUpdate(multipartFile, resultImageData);

        return imageRepository.save(resultImageData);
    }


    public ImageData importImageAndUpdate(MultipartFile multipartFile, ImageData inputImageData) throws IOException {
        Path uploadFilePath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
        log.debug(" ## Upload File Path : {}", uploadFilePath);
        if (!uploadFilePath.toFile().exists()) {
            uploadFilePath.toFile().mkdirs();
        }

        String importedLocation = uploadFilePath + multipartFile.getName();
        log.debug(" ## file import location : {}", importedLocation);
        File importFile = new File(importedLocation);

        multipartFile.transferTo(importFile); // 파일명 UUID로 변경

        inputImageData.setFileLocation(importedLocation);


        metadataRepository.save( ImageMetaData.builder().key("fileSize").numberValue(multipartFile.getSize()).type("number").imageData(inputImageData).build());

        SqsSendUtils.sendMessageToSQS(inputImageData.getUuid());

        return inputImageData;

    }


    public Optional<ImageData> retrieveImageById(ImageData reqImageDataData) {

        return imageRepository.findById(reqImageDataData.getUuid());
    }


    public ImageData updateImageById(ImageData reqImageDataData) {

        return imageRepository.save(reqImageDataData);
    }


    public void removeImageById(ImageData reqImageDataData) {
        imageRepository.deleteById(reqImageDataData.getUuid());
    }


}
