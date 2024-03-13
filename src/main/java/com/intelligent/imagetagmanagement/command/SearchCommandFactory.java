package com.intelligent.imagetagmanagement.command;

import com.intelligent.imagetagmanagement.command.searchcommands.*;
import com.intelligent.imagetagmanagement.exception.InvalidSearchException;
import com.intelligent.imagetagmanagement.model.SearchFilter;

public class SearchCommandFactory {
    public static SearchCommand getCommand(SearchFilter searchFilter) throws InvalidSearchException {
        return switch (searchFilter.getValueType().toLowerCase()) {
            case "string" -> getStringCommand(searchFilter.getCriteria());
            case "number" -> getNumberCommand(searchFilter.getCriteria());
            case "date" -> getDateCommand(searchFilter.getCriteria());
            default -> throw new InvalidSearchException();
        };
    }

    private static SearchCommand getStringCommand(String criteria) throws InvalidSearchException {
        return switch (criteria) {
            case "eq" -> new StringEqCommand();
            case "ne" -> new StringNeCommand();
            case "contains" -> new StringContainsCommand();
            case "startWith" -> new StringStartWithCommand();
            case "endWith" -> new StringEndWithCommand();
            default -> throw new InvalidSearchException();
        };
    }

    private static SearchCommand getNumberCommand(String criteria) throws InvalidSearchException {
        return switch (criteria) {
            case "eq" -> new NumberEqCommand();
            case "ne" -> new NumberNeCommand();
            case "gt" -> new NumberGtCommand();
            case "lt" -> new NumberLtCommand();
            case "goe" -> new NumberGoeCommand();
            case "loe" -> new NumberLoeCommand();
            default -> throw new InvalidSearchException();
        };
    }

    private static SearchCommand getDateCommand(String criteria) throws InvalidSearchException {
        return switch (criteria) {
            case "eq" -> new DateEqCommand();
            case "before" -> new DateBeforeCommand();
            case "after" -> new DateAfterCommand();
            default -> throw new InvalidSearchException();
        };
    }
}
