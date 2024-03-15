package com.intelligent.imagetagmanagement.util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ImageMetadataExtractorUtils {

    public static Map<String, String> extractFileExifData(File file) throws JpegProcessingException, IOException {
        Map<String, String> metadataMap = new HashMap<>();

        Metadata metadata = JpegMetadataReader.readMetadata(file);
//        log.info("metdata: {}", metadata);
        for (Directory directory : metadata.getDirectories()) {
//            log.info("directory: {}", directory);
            for (Tag tag : directory.getTags()) {
                metadataMap.put("EXIF, " + directory.getName() + ", " + tag.getTagName(), tag.getDescription());
            }
        }
        log.info("metadataMap: {}", metadataMap);

        return metadataMap;
    }

    public static void main(String[] args) throws JpegProcessingException, IOException {
        ImageMetadataExtractorUtils imageMetadataExtractorUtils = new ImageMetadataExtractorUtils();
        File file = new File("/Users/handongheon/Documents/20220424_194625.jpg");
        imageMetadataExtractorUtils.extractFileExifData(file);
    }
}
