package com.intelligent.imageTagManagement.repository;

import com.intelligent.imageTagManagement.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageData, String> {
}
