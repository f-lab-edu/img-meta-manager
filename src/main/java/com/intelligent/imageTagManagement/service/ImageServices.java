package com.intelligent.imageTagManagement.service;

import com.intelligent.imageTagManagement.model.ImageData;
import com.intelligent.imageTagManagement.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ImageServices {

    @Autowired
    private ImageRepository imageRepository;

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
        log.debug(" ## Upload File Path : {}", uploadFilePath.toString());
        if (!uploadFilePath.toFile().exists()) {
            uploadFilePath.toFile().mkdirs();
        }

        String importedLocation = uploadFilePath + multipartFile.getName();
        log.debug(" ## file import location : {}", importedLocation);
        File importFile = new File(importedLocation);

        multipartFile.transferTo(importFile);

        Map<String, String> inputMetadata = new HashMap<String, String>();
        inputMetadata.put("file-size", multipartFile.getSize() + "");
        // TODO : 기타 파일에서 확인할수 있는 메타 확인.

        inputImageData.setFileLocation(importedLocation);
        inputImageData.setMetadata(inputMetadata);

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
