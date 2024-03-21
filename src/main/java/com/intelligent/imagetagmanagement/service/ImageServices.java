package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.ImageMetaData;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.MetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImageServices {

    private final ImageRepository imageRepository;

    private final MetadataRepository metadataRepository;

    private final SqsSendService sqsSendService;

    @Value("${upload.directory}")
    private String uploadDirectory;


    @Autowired
    public ImageServices(ImageRepository imageRepository, MetadataRepository metadataRepository, SqsSendService sqsSendService) {
        this.imageRepository = imageRepository;
        this.metadataRepository = metadataRepository;
        this.sqsSendService = sqsSendService;
    }


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
            if(!uploadFilePath.toFile().mkdirs()){
                throw new IOException(uploadFilePath.toFile().getAbsolutePath());
            }
        }

        String importedLocation = uploadFilePath + inputImageData.getUuid();
        log.debug(" ## file import location : {}", importedLocation);
        File importFile = new File(importedLocation);

        multipartFile.transferTo(importFile);

        inputImageData.setFileLocation(importedLocation);

        // 이미지 추가 워크플로우 queue
        sqsSendService.sendMessageToSQS(inputImageData.getUuid());

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

//    public void saveAllMetadata(List<ImageMetaData> imageMetaDataList) {
//        metadataRepository.saveAll(imageMetaDataList);
//    }

    public void saveAllMetadataListMap(List<AbstractMap.SimpleEntry<String, String>> imageMetaDataList, ImageData imageData) {
        List<ImageMetaData> imageMetaDatas =  imageMetaDataList.stream().map(stringStringSimpleEntry -> {
            String key = stringStringSimpleEntry.getKey();
            String value = stringStringSimpleEntry.getValue();
            return  ImageMetaData.builder().type("string").key(key).stringValue(value).imageData(imageData).build();
        }).toList();

        metadataRepository.saveAll(imageMetaDatas);

    }



}
