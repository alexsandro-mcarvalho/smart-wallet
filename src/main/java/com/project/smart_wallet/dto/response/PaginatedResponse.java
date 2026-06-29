package com.project.smart_wallet.dto.response;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> data,
        Integer currentPage,
        Integer pageSize,
        Long totalItems,
        Integer totalPages,
        Boolean hasNext,
        Boolean hasPrevious
) {}