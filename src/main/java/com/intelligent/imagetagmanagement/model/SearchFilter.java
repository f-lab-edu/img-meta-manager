package com.intelligent.imagetagmanagement.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchFilter {
    private String key;
    private String operator;
    private String keyword;
    private String criteria;
    private String valueType;

    public long getValueToLong() {
        return Long.parseLong(keyword);
    }

    public LocalDateTime getValueToDate() {
        // TODO : keyword to LocalDateTime
        return LocalDateTime.now();
    }
}
