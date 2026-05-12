package com.project.smart_wallet.exceptions.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        int Status,
        String message,
        List<FieldErrorResponse> errors,
        String path) {
}
