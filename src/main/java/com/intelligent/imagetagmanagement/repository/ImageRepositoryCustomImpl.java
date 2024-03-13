package com.intelligent.imagetagmanagement.repository;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.ImageData;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.intelligent.imagetagmanagement.model.QImageData.*;
import static com.intelligent.imagetagmanagement.model.QImageMetaData.*;

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
                case "AND":
                    builder.and(getExpression(searchFilter));
                    break;
                case "OR":
                    builder.or(getExpression(searchFilter));
                    break;
                default:
                    throw new InvalidSearchException();

            }
        }

        return jpaQueryFactory
                .select(imageData)
                .from(imageData)
                .join(imageData.metadata, imageMetaData)
                .where(builder)
                .fetch();

    }

    public BooleanExpression getExpression(SearchFilter searchFilter) throws InvalidSearchException {
        return switch (searchFilter.getValueType().toLowerCase()) {
            case "string" -> switch (searchFilter.getCriteria()) {
                case "eq" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.eq(searchFilter.getKeyword()));
                case "ne" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.ne(searchFilter.getKeyword()));
                case "contains" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.contains(searchFilter.getKeyword()));
                case "startWith" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.startsWith(searchFilter.getKeyword()));
                case "endWith" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.endsWith(searchFilter.getKeyword()));
                case "like" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.like(searchFilter.getKeyword()));

                default -> throw new InvalidSearchException();
            };
            case "number" -> switch (searchFilter.getCriteria()) {
                case "eq" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.eq(searchFilter.getValueToLong()));
                case "ne" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.ne(searchFilter.getValueToLong()));
                case "gt" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.gt(searchFilter.getValueToLong()));
                case "lt" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.lt(searchFilter.getValueToLong()));
                case "goe" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.goe(searchFilter.getValueToLong()));
                case "loe" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.loe(searchFilter.getValueToLong()));

                default -> throw new InvalidSearchException();
            };
            case "date" -> switch (searchFilter.getCriteria()) {
                case "eq" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.eq(searchFilter.getValueToDate()));
                case "before" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.before(searchFilter.getValueToDate()));
                case "after" ->
                        imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.after(searchFilter.getValueToDate()));

                default -> throw new InvalidSearchException();
            };
            default -> throw new InvalidSearchException();
        };

    }
}
