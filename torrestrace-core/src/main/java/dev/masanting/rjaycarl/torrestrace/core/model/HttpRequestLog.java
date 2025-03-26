package dev.masanting.rjaycarl.torrestrace.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public record HttpRequestLog(
        UUID id,
        String method,
        String url,
        Map<String, String> headers,
        String body,
        Instant timestamp
) {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static HttpRequestLog create(String method, String url, Map<String, String> headers, String body) {
        return new HttpRequestLog(
                null,
                method,
                url,
                headers,
                body,
                Instant.now()
        );
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert log to JSON", e);
        }
    }
}
