package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.model.ImageData;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.intelligent.imagetagmanagement.model.QImageData.imageData;
import static com.intelligent.imagetagmanagement.model.QImageMetaData.imageMetaData;

@Slf4j
@Repository
public class ImageQueryRepositoryCustomImpl implements ImageQueryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ImageQueryRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ImageData> SearchByFilter(Map<String, String> filterData) {

        log.debug(" test ## filterdata  : {}", filterData.size());

        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, String> entry : filterData.entrySet()) {
            builder.and(imageMetaData.key.eq(entry.getKey())
                    .and(imageMetaData.value.contains(entry.getValue())));

        }

        return jpaQueryFactory
                .select(imageData)
                .from(imageData)
                .join(imageData.metadata, imageMetaData)
                .where(builder)
                .fetch();

    }
}
