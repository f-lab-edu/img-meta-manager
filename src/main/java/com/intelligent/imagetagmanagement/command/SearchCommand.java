package com.intelligent.imagetagmanagement.command;

import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.SearchFilter;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface SearchCommand {
    BooleanExpression execute(SearchFilter searchFilter) throws InvalidSearchException;
}
