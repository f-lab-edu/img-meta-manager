package com.intelligent.imagetagmanagement.util;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class ImageMetadataExtractorUtils {
    public static List<AbstractMap.SimpleEntry<String, String>> extractFileExifData(File file) throws JpegProcessingException, IOException {
        Metadata metadata = JpegMetadataReader.readMetadata(file);
        Iterable<Directory> directories = metadata.getDirectories();

        return StreamSupport.stream(directories.spliterator(), false)
                .flatMap(directory -> directory.getTags().stream()
                        .map(tag -> new AbstractMap.SimpleEntry<>(
                                "EXIF, " + directory.getName() + ", " + tag.getTagName(),
                                tag.getDescription())))
                .collect(Collectors.toList());
    }
}
