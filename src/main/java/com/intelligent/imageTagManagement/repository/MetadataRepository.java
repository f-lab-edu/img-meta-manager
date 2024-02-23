package com.intelligent.imageTagManagement.repository;

import com.intelligent.imageTagManagement.model.ImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<ImageMetaData, String>, ImageQueryRepositoryCustom {

}