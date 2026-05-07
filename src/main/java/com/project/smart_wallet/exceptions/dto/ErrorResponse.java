package com.project.smart_wallet.exceptions.dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        HttpStatus Status,
        String message,
        List<FieldErrorResponse> errors,
        String path) {
}
