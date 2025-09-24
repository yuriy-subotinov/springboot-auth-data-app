package dev.subotinov.authapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.subotinov.authapi.api.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ErrorResponseWriter {
    private final ObjectMapper mapper;


    public void write(HttpServletResponse resp, String error, String message, int status) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.getWriter().write(
                mapper.writeValueAsString(new ErrorResponse(error, message, status, Instant.now()))
        );
    }
}