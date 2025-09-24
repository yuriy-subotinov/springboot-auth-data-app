package dev.subotinov.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpClientsConfig {

    @Bean
    public RestClient dataApiClient(AppProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.dataApi().baseUrl())
                .defaultHeader("X-Internal-Token", properties.dataApi().internalToken())
                .build();
    }
}
