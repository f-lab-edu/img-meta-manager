package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.ImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<ImageMetaData, String>, MetadataRepositoryCustom {

}