package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.WorkQueueData;

import java.util.List;

public interface WorkQueueRepositoryCustom {
    List<WorkQueueData> findForWorkflow();
}
