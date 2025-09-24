package dev.subotinov.authapi.api.dto;

import jakarta.validation.constraints.NotBlank;

public record TransformRequest(@NotBlank(message = "text must be not blank") String text) {}
