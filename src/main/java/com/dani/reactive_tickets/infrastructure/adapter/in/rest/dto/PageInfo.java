package com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto;

public record PageInfo(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {
    public static PageInfo of(int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean hasNext = page < totalPages - 1;
        boolean hasPrevious = page > 0;

        return new PageInfo(page, size, totalElements, totalPages, hasNext, hasPrevious);
    }
}
