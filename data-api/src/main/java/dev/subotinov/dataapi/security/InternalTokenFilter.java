package dev.subotinov.dataapi.security;

import dev.subotinov.dataapi.config.InternalTokenProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


public class InternalTokenFilter extends OncePerRequestFilter {

    private final String expectedToken;
    private final HandlerExceptionResolver resolver;

    public InternalTokenFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, InternalTokenProperties props) {
        this.resolver = resolver;
        this.expectedToken = props.token();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("X-Internal-Token");
        if (!StringUtils.hasText(expectedToken) || !StringUtils.hasText(token) || !token.equals(expectedToken)) {
            resolver.resolveException(request, response, null, new InternalTokenException("Missing or invalid X-Internal-Token"));
            return;
        }
        filterChain.doFilter(request, response);

    }
}
