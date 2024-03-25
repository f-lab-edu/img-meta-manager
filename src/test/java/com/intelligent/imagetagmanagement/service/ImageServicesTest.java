package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.ImageMetaData;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.MetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ActiveProfiles("local")
public class ImageServicesTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private ImageServices imageServices;


    private void setSampleMetadataList(ImageData imageData) {
        List<ImageMetaData> resultMetadatas = new ArrayList<>();

        resultMetadatas.add(ImageMetaData.builder().key("extension").stringValue("jpeg").type("string").imageData(imageData).build());
        resultMetadatas.add(ImageMetaData.builder().key("extension").stringValue("jpeg").type("string").imageData(imageData).build());
        resultMetadatas.add(ImageMetaData.builder().key("fileSize").numberValue(1540L).type("number").imageData(imageData).build());

        metadataRepository.saveAll(resultMetadatas);
    }

    @BeforeEach
    public void beforeTest() {
//        List<ImageData> imageDataList = new ArrayList<>();
//        imageDataList.add(ImageData.builder().name("이미지").fileName("이미지.jpg").fileLocation("/이미지.jpg").build());
//        imageDataList.add(ImageData.builder().name("이미지1").fileName("이미지1.jpg").fileLocation("/이미지1.jpg").build());
//        imageDataList.add(ImageData.builder().name("이미지2").fileName("이미지2.jpg").fileLocation("/이미지2.jpg").build());
//        imageDataList.add(ImageData.builder().name("이미지3").fileName("이미지3.jpeg").fileLocation("/이미지3.jpeg").build());
//        imageRepository.saveAll(imageDataList);
//
//        imageDataList.forEach(imageData -> {
//            setSampleMetadataList(imageData);
//        });
    }

    @Test
    void findByKeyword() {
//        assertThat(imageServices.findByKeyword("이미").size()).isEqualTo(4);
//        assertThat(imageServices.findByKeyword("jpg").size()).isEqualTo(3);
//        assertThat(imageServices.findByKeyword("none").size()).isEqualTo(0);

//        assertThat(imageServices.findByKeyword("100203").size()).isEqualTo(4);
    }

    @Test
    void searchByFilter() throws InvalidSearchException {
//        List<SearchFilter> searchFilter = new ArrayList<>();
//        searchFilter.add(SearchFilter.builder().key("fileSize").keyword("0").operator("and").criteria("goe").valueType("number").build());
//
//        System.out.println(imageRepository.searchByFilter(searchFilter).size());
//
//        assertThat(imageRepository.searchByFilter(searchFilter).size()).isGreaterThan(3);
    }
}
