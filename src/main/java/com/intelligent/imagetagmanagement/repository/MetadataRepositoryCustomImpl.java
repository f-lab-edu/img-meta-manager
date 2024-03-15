package com.intelligent.imagetagmanagement.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.intelligent.imagetagmanagement.model.QImageMetaData.imageMetaData;

public class MetadataRepositoryCustomImpl implements MetadataRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MetadataRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<String> getAllMetaKeys() {

        return jpaQueryFactory.select(imageMetaData.key).from(imageMetaData).distinct().fetch();

    }
}
