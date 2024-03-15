package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.WorkQueueData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
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

    @Override
    public long getTotalFailedWorkflowCount() {
        Long failedWorkflowCount = jpaQueryFactory
                .select(workQueueData.count())
                .from(workQueueData)
                .where(workQueueData.work_status.eq("failed"))
                .fetchOne();

        return failedWorkflowCount != null ? failedWorkflowCount : 0L;
    }

    @Override
    public long getTotalSuccessWorkflowCount() {
        Long completedWorkflowCount = jpaQueryFactory
                .select(workQueueData.count())
                .from(workQueueData)
                .where(workQueueData.work_status.eq("completed"))
                .fetchOne();

        return completedWorkflowCount != null ? completedWorkflowCount : 0L;
    }

    @Override
    public long getTodayFailedWorkflowCount() {
        Long failedWorkflowCount = jpaQueryFactory
                .select(workQueueData.count())
                .from(workQueueData)
                .where(workQueueData.work_status.eq("failed").and(workQueueData.created_date.after(LocalDate.now().atStartOfDay())))
                .fetchOne();
        return failedWorkflowCount != null ? failedWorkflowCount : 0L;
    }

    @Override
    public long getTodaySuccessWorkflowCount() {
        Long completedWorkflowCount = jpaQueryFactory
                .select(workQueueData.count())
                .from(workQueueData)
                .where(workQueueData.work_status.eq("completed").and(workQueueData.created_date.after(LocalDate.now().atStartOfDay())))
                .fetchOne();
        return completedWorkflowCount != null ? completedWorkflowCount : 0L;
    }
}
