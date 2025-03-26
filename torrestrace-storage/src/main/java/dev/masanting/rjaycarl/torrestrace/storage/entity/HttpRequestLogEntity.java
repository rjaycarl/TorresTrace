package dev.masanting.rjaycarl.torrestrace.storage.entity;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Entity
@Table(name = "http_request_log")
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class HttpRequestLogEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> headers;

    public static HttpRequestLogEntity fromModel(HttpRequestLog model) {
        return HttpRequestLogEntity.builder()
                .method(model.method())
                .url(model.url())
                .body(model.body())
                .timestamp(model.timestamp() != null ? model.timestamp() : Instant.now())
                .headers(model.headers())
                .build();
    }

    public HttpRequestLog toModel() {
        return new HttpRequestLog(id, method, url, headers, body, timestamp);
    }
}
