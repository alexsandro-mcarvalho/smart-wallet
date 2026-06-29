package com.project.smart_wallet.mapper;

import com.project.smart_wallet.dto.response.PaginatedResponse;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static <T> PaginatedResponse<T> toResponse(Page<T> page) {
        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
