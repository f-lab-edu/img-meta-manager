package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageData, String>, ImageRepositoryCustom {
        @Query("SELECT i " +
            "FROM ImageData i join i.metadata m " +
            "WHERE i.name LIKE %:keyword% " +
            "OR i.fileName LIKE %:keyword% " +
            "OR i.fileLocation LIKE %:keyword% ")
        List<ImageData> findByKeyword(@Param("keyword") String keyword);
}