package com.intelligent.imageTagManagement.controller;

import com.intelligent.imageTagManagement.model.ImageData;
import com.intelligent.imageTagManagement.service.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageServices imageServices;

    @GetMapping("/{uuid}")
    public ResponseEntity<ImageData> getImageById(@RequestBody ImageData reqImageDataData) {
        return imageServices.retrieveImageById(reqImageDataData)
                .map(imageData -> ResponseEntity.ok().body(imageData))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageData> registerImage(@RequestPart("file") MultipartFile file, @RequestPart("imageBody") ImageData imageDataBody) throws IOException {
        return imageServices.registerImage(file, imageDataBody)
                .map(imageData -> ResponseEntity.ok().body(imageData))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public ResponseEntity<ImageData> removeImage(@RequestBody ImageData reqImageDataData) {
        return imageServices.removeImageById(reqImageDataData)
                .map(imageData -> ResponseEntity.ok().body(imageData))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PatchMapping
    public ResponseEntity<ImageData> updateImage(@RequestBody ImageData reqImageDataData) {
        return imageServices.updateImageById(reqImageDataData)
                .map(imageData -> ResponseEntity.ok().body(imageData))
                .orElse(ResponseEntity.badRequest().build());
    }

}
