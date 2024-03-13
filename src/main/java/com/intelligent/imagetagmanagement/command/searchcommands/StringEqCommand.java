package com.intelligent.imagetagmanagement.command.searchcommands;

import com.intelligent.imagetagmanagement.command.SearchCommand;
import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.querydsl.core.types.dsl.BooleanExpression;

import static com.intelligent.imagetagmanagement.model.QImageMetaData.imageMetaData;

public class StringEqCommand implements SearchCommand {
    @Override
    public BooleanExpression execute(SearchFilter searchFilter) throws InvalidSearchException {
        return imageMetaData.key.eq(searchFilter.getKey()).and(imageMetaData.stringValue.eq(searchFilter.getKeyword()));

    }
}
