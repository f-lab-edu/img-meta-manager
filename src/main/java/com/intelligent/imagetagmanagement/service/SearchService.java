package com.intelligent.imagetagmanagement.service;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.intelligent.imagetagmanagement.repository.ImageRepository;
import com.intelligent.imagetagmanagement.repository.MetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    public List<ImageData> findByKeyword(String keyword) {
        return imageRepository.findByKeyword(keyword);
    }

    public List<ImageData> searchByFilter(List<SearchFilter> filterData) throws InvalidSearchException {
        return imageRepository.searchByFilter(filterData);
    }

    public List<String> getAllMetaKeys(){
        return metadataRepository.getAllMetaKeys();
    }
}
