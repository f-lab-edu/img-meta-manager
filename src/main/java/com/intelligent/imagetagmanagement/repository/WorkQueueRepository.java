package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.WorkQueueData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkQueueRepository extends JpaRepository<WorkQueueData, String>, WorkQueueRepositoryCustom {
}
