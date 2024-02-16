package com.example.imagetagmanagement.service.impl;

import com.example.imagetagmanagement.model.Image;
import com.example.imagetagmanagement.repository.ImageRepository;
import com.example.imagetagmanagement.service.ImageServices;
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
public class ImageServicesImpl implements ImageServices {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public Optional<Image> registerImage(MultipartFile multipartFile, Image image) {
        Image resultImage;
        // validation
        resultImage = imageRepository.save(image);

        try {
            resultImage = importImageAndUpadate(multipartFile, resultImage);

            imageRepository.save(resultImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.of(resultImage);
    }


    @Override
    public Image importImageAndUpadate(MultipartFile multipartFile, Image inputImage) throws IOException {
        Path uploadFilePath = Paths.get(uploadDirectory).toAbsolutePath().normalize();
        log.info(uploadFilePath.toString());
        if (!uploadFilePath.toFile().exists()) {
            uploadFilePath.toFile().mkdirs();
        }

        String importedLocation = uploadFilePath + multipartFile.getName();
        log.info(importedLocation);
        File importFile = new File(importedLocation);

        multipartFile.transferTo(importFile);

        Map<String, String> inputMetadata = new HashMap<String, String>();
        inputMetadata.put("file-size", multipartFile.getSize() + "");
        // TODO : 기타 파일에서 확인할수 있는 메타 확인.

        inputImage.setFileLocation(importedLocation);
        inputImage.setMetadata(inputMetadata);

        return inputImage;

    }

    @Override
    public Optional<Image> retrieveImageById(Image reqImageData) {

        return imageRepository.findById(reqImageData.getUuid());
    }

    @Override
    public Optional<Image> updateImageById(Image reqImageData) {

        return Optional.of(imageRepository.save(reqImageData));
    }

    @Override
    public Optional<Image> removeImageById(Image reqImageData) {
        imageRepository.deleteById(reqImageData.getUuid());
        return Optional.empty();
    }
}
