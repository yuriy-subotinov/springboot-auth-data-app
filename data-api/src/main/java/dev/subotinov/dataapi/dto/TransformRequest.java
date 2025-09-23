package dev.subotinov.dataapi.dto;

import jakarta.validation.constraints.NotBlank;

public record TransformRequest(@NotBlank(message = "text must be not blank") String text) {
}
