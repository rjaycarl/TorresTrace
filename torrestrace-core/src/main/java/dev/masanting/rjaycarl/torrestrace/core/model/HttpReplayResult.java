package dev.masanting.rjaycarl.torrestrace.core.model;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public record HttpReplayResult(
        int statusCode,
        String responseBody,
        long durationMs
) {
}