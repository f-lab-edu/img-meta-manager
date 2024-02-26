package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.ImageData;

import java.util.List;
import java.util.Map;

public interface ImageQueryRepositoryCustom {

    List<ImageData> SearchByFilter(Map<String, String> filterData);

}
