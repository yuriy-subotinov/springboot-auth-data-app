package dev.subotinov.authapi.api.dto;

import java.time.Instant;

public record ErrorResponse(
        String error,
        String message,
        int status,
        Instant timestamp
) {}
