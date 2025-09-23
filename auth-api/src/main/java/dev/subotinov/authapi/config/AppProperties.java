package dev.subotinov.authapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        JwtProps jwt,
        DataApiProps dataApi
) {
    public record JwtProps(String secret, long expiration) {}
    public record DataApiProps(String baseUrl, String internalToken) {}
}