package com.intelligent.imageTagManagement.service;

import com.intelligent.imageTagManagement.model.ImageData;
import com.intelligent.imageTagManagement.model.ImageMetaData;
import com.intelligent.imageTagManagement.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ImageServicesTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageServices imageServices;

    private List<ImageMetaData> 샘플메타데이터() {
        List<ImageMetaData> resultMetadatas = new ArrayList<>();
        ImageMetaData metadata = new ImageMetaData();
        metadata.setKey("extension");
        metadata.setValue("jpeg");

        ImageMetaData metadata2 = new ImageMetaData();
        metadata2.setKey("extension");
        metadata2.setValue("jpeg");

        resultMetadatas.add(metadata);
        resultMetadatas.add(metadata2);

        return resultMetadatas;
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

    @Test
    void findByKeyword() {
        List<ImageData> imageDataList = new ArrayList<>();
        imageDataList.add(ImageData.builder().name("이미지").fileName("이미지.jpg").fileLocation("/이미지.jpg").metadata(샘플메타데이터()).build());
        imageDataList.add(ImageData.builder().name("이미지").fileName("이미지.jpg").fileLocation("/이미지.jpg").metadata(샘플메타데이터()).build());
        imageDataList.add(ImageData.builder().name("이미지").fileName("이미지.jpg").fileLocation("/이미지.jpg").metadata(샘플메타데이터()).build());
        imageDataList.add(ImageData.builder().name("이미지").fileName("이미지.jpeg").fileLocation("/이미지.jpeg").metadata(샘플메타데이터()).build());
        imageRepository.saveAll(imageDataList);


        assertThat(imageServices.findByKeyword("이미지").size()).isEqualTo(4);
        assertThat(imageServices.findByKeyword("jpg").size()).isEqualTo(3);
        assertThat(imageServices.findByKeyword("none").size()).isEqualTo(0);

        assertThat(imageServices.findByKeyword("100203").size()).isEqualTo(4);
    }

    @Test
    void searchByFilter() {
        Map<String, String > searchFilter = new HashMap<>();
        searchFilter.put("fileSize", "150");

        imageRepository.SearchByFilter(searchFilter);

    }
}
