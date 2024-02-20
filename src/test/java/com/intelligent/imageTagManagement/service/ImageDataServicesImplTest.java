package com.intelligent.imageTagManagement.service;

import com.intelligent.imageTagManagement.model.ImageData;
import com.intelligent.imageTagManagement.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ImageDataServicesImplTest {

    @Autowired
    private ImageRepository imageRepository;

    private Map<String, String> 샘플메타데이터() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("extension", "jpge");
        metadata.put("filesize", "100203");
        return metadata;
    }

    @Test
    @DisplayName("이미지 저장 및 조회")
    void 이미지저장및조회() {
        ImageData imageData = ImageData.builder().name("이미지").fileName("이미지.jpg").fileLocation("/이미지.jpg").metadata(샘플메타데이터()).build();
        imageRepository.save(imageData);

        Optional<ImageData> retrievedImage = imageRepository.findById(imageData.getUuid());

        retrievedImage.ifPresent(value -> {
            assertThat(value.getName()).isEqualTo("이미지");
            assertThat(value.getUuid()).isNotBlank();
            assertThat(value.getFileName()).isEqualTo("이미지.jpg");
            assertThat(value.getFileLocation()).isEqualTo("/이미지.jpg");
            assertThat(value.getMetadata()).isEqualTo(샘플메타데이터());

        });


    }
}
