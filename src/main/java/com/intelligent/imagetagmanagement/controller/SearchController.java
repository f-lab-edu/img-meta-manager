package com.intelligent.imagetagmanagement.controller;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.intelligent.imagetagmanagement.service.ImageServices;
import com.intelligent.imagetagmanagement.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ImageServices imageServices;

    @Autowired
    private SearchService searchService;


    @GetMapping
    public ResponseEntity<List<ImageData>> searchImage(@RequestBody List<SearchFilter> searchFilters) throws InvalidSearchException {
        return ResponseEntity.ok().body(searchService.searchByFilter(searchFilters));
    }

    @GetMapping("/all-meta-keys")
    public ResponseEntity<List<String>> getAllMetaKeys() {
        return ResponseEntity.ok().body(searchService.getAllMetaKeys());
    }
}
