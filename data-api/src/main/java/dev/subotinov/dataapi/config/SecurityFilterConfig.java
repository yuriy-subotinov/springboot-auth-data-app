package dev.subotinov.dataapi.config;

import dev.subotinov.dataapi.security.InternalTokenFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class SecurityFilterConfig {

    @Bean
    public FilterRegistrationBean<InternalTokenFilter> internalTokenFilter
            (@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, InternalTokenProperties props)
    {
        InternalTokenFilter filter = new InternalTokenFilter(resolver, props);
        var reg = new FilterRegistrationBean<>(filter);
        reg.setName("internalTokenFilter");
        reg.addUrlPatterns("/*");
        reg.setOrder(1);
        return reg;

    }
}
