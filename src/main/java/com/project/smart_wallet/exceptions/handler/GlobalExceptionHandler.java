package com.project.smart_wallet.exceptions.handler;

import com.project.smart_wallet.exceptions.dto.ErrorResponse;
import com.project.smart_wallet.exceptions.custom.ConflictException;
import com.project.smart_wallet.exceptions.custom.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                List.of(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(ConflictException ex, HttpServletRequest request) {
        return new ErrorResponse(
                Instant.now(),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                List.of(),
                request.getRequestURI()
        );
    }
}
