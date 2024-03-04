package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.SearchFilter;

import java.util.List;

public interface ImageRepositoryCustom {

    List<ImageData> searchByFilter(List<SearchFilter> filterData) throws InvalidSearchException;

}
