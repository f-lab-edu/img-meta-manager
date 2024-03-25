package com.intelligent.imagetagmanagement.controller;

import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.service.ImageServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@Slf4j
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
        log.debug("Registering new image {}", imageDataBody);
        log.debug("File name {}", file.getOriginalFilename());
        return ResponseEntity.ok().body(imageServices.registerImage(file, imageDataBody));
    }

    @DeleteMapping
    public ResponseEntity<ImageData> removeImage(@RequestBody ImageData reqImageDataData) {
        imageServices.removeImageById(reqImageDataData);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<ImageData> updateImage(@RequestBody ImageData reqImageDataData) {
        return ResponseEntity.ok().body(imageServices.updateImageById(reqImageDataData));

    }


}
