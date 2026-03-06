package com.dani.reactive_tickets.infrastructure.adapter.in.rest.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.server.ServerRequest;

public class PaginationUtil {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    private PaginationUtil() {
    }

    public static Pageable extractPageable(ServerRequest request) {
        int page = extractPage(request);
        int size = extractSize(request);

        return PageRequest.of(page, size);
    }

    private static int extractPage(ServerRequest request) {
        return request.queryParam("page")
                .map(PaginationUtil::parsePositivePage)
                .orElse(DEFAULT_PAGE);
    }

    private static int extractSize(ServerRequest request) {
        return request.queryParam("size")
                .map(PaginationUtil::parsePositiveSize)
                .map(size -> Math.min(size, MAX_SIZE))
                .orElse(DEFAULT_SIZE);
    }

    private static int parsePositivePage(String value) {
        try {
            int parsed = Integer.parseInt(value);
            return Math.max(0, parsed);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE;
        }
    }

    private static int parsePositiveSize(String value) {
        try {
            int parsed = Integer.parseInt(value);
            return Math.max(1, parsed);  // Size debe ser al menos 1
        } catch (NumberFormatException e) {
            return DEFAULT_SIZE;
        }
    }

}
