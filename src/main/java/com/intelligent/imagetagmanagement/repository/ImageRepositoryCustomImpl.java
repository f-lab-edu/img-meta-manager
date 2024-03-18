package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.command.SearchCommandFactory;
import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.intelligent.imagetagmanagement.model.QImageData.imageData;
import static com.intelligent.imagetagmanagement.model.QImageMetaData.imageMetaData;

@Slf4j
@Repository
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ImageRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ImageData> searchByFilter(List<SearchFilter> filterData) throws InvalidSearchException {

        log.debug(" test ## filterdata  : {}", filterData.size());

        BooleanBuilder builder = new BooleanBuilder();

        for (SearchFilter searchFilter : filterData) {
            switch (searchFilter.getOperator().toUpperCase()) {
                case "AND" -> builder.and(getExpression(searchFilter));
                case "OR" -> builder.or(getExpression(searchFilter));
                default -> throw new InvalidSearchException();
            }
        }

        return jpaQueryFactory
                .select(imageData)
                .from(imageData)
                .join(imageData.metadata, imageMetaData)
                .where(builder)
                .fetch();

    }

    @Override
    public long getTotalUploadCount() {
        Long totalUploadCount = jpaQueryFactory
                .select(imageData.count())
                .from(imageData)
                .fetchOne();
        return totalUploadCount != null ? totalUploadCount : 0L;
    }

    @Override
    public long getTodayUploadCount() {

        Long todayUploadCount = jpaQueryFactory
                .select(imageData.count())
                .from(imageData)
                .where(imageData.uploadDate.after(LocalDate.now().atStartOfDay()))
                .fetchOne();
        return todayUploadCount != null ? todayUploadCount : 0L;
    }

    public BooleanExpression getExpression(SearchFilter searchFilter) throws InvalidSearchException {
        return SearchCommandFactory.getCommand(searchFilter).apply(searchFilter);
    }
}
