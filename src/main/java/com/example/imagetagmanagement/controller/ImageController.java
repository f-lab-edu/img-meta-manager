package com.example.imagetagmanagement.controller;

import com.example.imagetagmanagement.model.Image;
import com.example.imagetagmanagement.service.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageServices imageServices;

    @GetMapping("/{uuid}")
    public ResponseEntity<Image> getImageById(@PathVariable String uuid) {
        return imageServices.retrieveImageById(uuid)
                .map(image -> ResponseEntity.ok().body(image))
                .orElse(ResponseEntity.notFound().build());
    }

}
