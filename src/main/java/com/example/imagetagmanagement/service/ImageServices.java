package com.example.imagetagmanagement.service;

import com.example.imagetagmanagement.model.Image;

import java.util.Optional;

public interface ImageServices {
    // 이미지 저장
    void registerImage();
    // 이미지 조회 by id
    Optional<Image> retrieveImageById(String uuid);
}
