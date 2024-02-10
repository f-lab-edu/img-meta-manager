package com.example.imagetagmanagement.service.impl;

import com.example.imagetagmanagement.model.Image;
import com.example.imagetagmanagement.repository.ImageRepository;
import com.example.imagetagmanagement.service.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServicesImpl implements ImageServices {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void registerImage() {

    }

    @Override
    public Optional<Image> retrieveImageById(String uuid) {
        return imageRepository.findById(uuid);
    }
}
