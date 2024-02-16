package com.example.imagetagmanagement.service;

import com.example.imagetagmanagement.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageServices {
    // 이미지 저장
    Optional<Image> registerImage(MultipartFile multipartFile, Image image);
    // 이미지 물리파일 저장
    Image importImageAndUpadate(MultipartFile multipartFile, Image inputImage)  throws IOException;
    // 이미지 조회 by id
    Optional<Image> retrieveImageById(Image reqImageData);

    Optional<Image> updateImageById(Image reqImageData);

    Optional<Image> removeImageById(Image reqImageData);

}
