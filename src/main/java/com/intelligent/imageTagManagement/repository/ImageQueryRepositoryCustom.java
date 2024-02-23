package com.intelligent.imageTagManagement.repository;

import com.intelligent.imageTagManagement.model.ImageData;

import java.util.List;
import java.util.Map;

public interface ImageQueryRepositoryCustom {

    List<ImageData> SearchByFilter(Map<String, String> filterData);

}
