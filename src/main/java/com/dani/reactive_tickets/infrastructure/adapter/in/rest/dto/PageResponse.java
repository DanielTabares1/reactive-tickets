package com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        PageInfo pageInfo
) {
    public static <T> PageResponse<T> of(List<T> content, PageInfo pageInfo) {
        return new PageResponse<>(content, pageInfo);
    }
}
