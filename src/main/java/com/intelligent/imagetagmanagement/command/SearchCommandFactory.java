package com.intelligent.imagetagmanagement.command;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.intelligent.imagetagmanagement.model.QImageMetaData.imageMetaData;

@Component
public class SearchCommandFactory { // factory 아님

    private  final Map<String, Map<String, Function<SearchFilter, BooleanExpression>>> functionalMap = new HashMap<>(Map.of(
            "string", Map.of(
                    "eq", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.eq(searchFilter.getKeyword())),
                    "ne", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.ne(searchFilter.getKeyword())),
                    "contains", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.contains(searchFilter.getKeyword())),
                    "startWith", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.startsWith(searchFilter.getKeyword())),
                    "endWith", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.endsWith(searchFilter.getKeyword())),
                    "like", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.like(searchFilter.getKeyword()))
            ),
            "number", Map.of(
                    "eq", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.eq(searchFilter.getValueToLong())),
                    "ne", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.ne(searchFilter.getValueToLong())),
                    "gt", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.gt(searchFilter.getValueToLong())),
                    "lt", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.lt(searchFilter.getValueToLong())),
                    "goe", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.goe(searchFilter.getValueToLong())),
                    "loe", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.numberValue.loe(searchFilter.getValueToLong()))
            ),
            "date", Map.of(
                    "eq", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.eq(searchFilter.getValueToDate())),
                    "after", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.after(searchFilter.getValueToDate())),
                    "before", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.before(searchFilter.getValueToDate())),
                    "ne", (searchFilter) -> imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.dateValue.ne(searchFilter.getValueToDate()))
            )
    ));

    public  Function<SearchFilter, BooleanExpression> getCommand(SearchFilter searchFilter) throws InvalidSearchException {
        validateSearchFilter(searchFilter);
        return functionalMap.get(searchFilter.getValueType().toLowerCase()).get(searchFilter.getCriteria());
    }

    private  void validateSearchFilter(SearchFilter searchFilter) throws InvalidSearchException {
        String valueType = searchFilter.getValueType().toLowerCase();
        String criteria = searchFilter.getCriteria();
        if (!functionalMap.containsKey(valueType)) {
            throw new InvalidSearchException();
        }
        if (!functionalMap.get(searchFilter.getValueType().toLowerCase()).containsKey(criteria)) {
            throw new InvalidSearchException();
        }
    }
}