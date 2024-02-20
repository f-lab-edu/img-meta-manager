package com.inteli.imagetagmanagement.repository;

import com.inteli.imagetagmanagement.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
}
