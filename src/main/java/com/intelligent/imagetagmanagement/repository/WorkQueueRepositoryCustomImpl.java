package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.WorkQueueData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.intelligent.imagetagmanagement.model.QWorkQueueData.workQueueData;


@Slf4j
public class WorkQueueRepositoryCustomImpl implements WorkQueueRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public WorkQueueRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<WorkQueueData> findForWorkflow() {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(workQueueData.work_status.eq("ready"));

        return jpaQueryFactory
                .select(workQueueData)
                .from(workQueueData)
                .where(builder)
                .fetch();

    }
}
