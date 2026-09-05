package com.project.smart_wallet.dto.response;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String message,
        List<FieldErrorResponse> errors,
        String path) {
}
