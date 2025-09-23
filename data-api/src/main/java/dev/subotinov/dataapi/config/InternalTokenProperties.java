package dev.subotinov.dataapi.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "internal")
public record InternalTokenProperties(@NotBlank String token) { }
