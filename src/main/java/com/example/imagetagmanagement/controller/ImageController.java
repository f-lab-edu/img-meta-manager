package com.example.imagetagmanagement.controller;

import com.example.imagetagmanagement.model.Image;
import com.example.imagetagmanagement.service.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageServices imageServices;

    @GetMapping("/{uuid}")
    public ResponseEntity<Image> getImageById(@RequestBody Image reqImageData) {
        return imageServices.retrieveImageById(reqImageData)
                .map(image -> ResponseEntity.ok().body(image))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> registerImage(@RequestPart("file") MultipartFile file, @RequestPart("imageBody") Image imageBody) {
        return imageServices.registerImage(file, imageBody)
                .map(image -> ResponseEntity.ok().body(image))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public ResponseEntity<Image> removeImage(@RequestBody Image reqImageData) {
        return imageServices.removeImageById(reqImageData)
                .map(image -> ResponseEntity.ok().body(image))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping
    public ResponseEntity<Image> updateImage(@RequestBody Image reqImageData) {
        return imageServices.updateImageById(reqImageData)
                .map(image -> ResponseEntity.ok().body(image))
                .orElse(ResponseEntity.badRequest().build());
    }

}
