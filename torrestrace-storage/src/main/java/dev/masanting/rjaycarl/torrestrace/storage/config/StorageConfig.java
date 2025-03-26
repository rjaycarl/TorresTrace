package dev.masanting.rjaycarl.torrestrace.storage.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.properties.TorresTraceProperties;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import dev.masanting.rjaycarl.torrestrace.storage.repository.HttpRequestLogRepository;
import dev.masanting.rjaycarl.torrestrace.storage.service.DatabaseRequestLogger;
import dev.masanting.rjaycarl.torrestrace.storage.service.FileHttpRequestLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Configuration
public class StorageConfig {

    @Autowired
    private HttpRequestLogRepository repository;

    @Bean(name = "fileLogger")
    public RequestLogger fileHttpRequestLogger(TorresTraceProperties properties, ObjectMapper objectMapper) throws IOException {
        return new FileHttpRequestLogger(properties, objectMapper);
    }

    @Bean(name = "inMemoryLogger")
    public RequestLogger inMemoryRequestLogger() {
        return new RequestLogger() {
            @Override
            public HttpRequestLog save(HttpRequestLog requestLog) {
                throw new UnsupportedOperationException("In-Memory Logger is decommissioned");
            }

            @Override
            public java.util.List<HttpRequestLog> findAll() {
                return java.util.List.of();
            }

            @Override
            public java.util.Optional<HttpRequestLog> findById(java.util.UUID id) {
                return java.util.Optional.empty();
            }

            @Override
            public void deletedById(java.util.UUID id) {
            }

            @Override
            public void clearAll() {
            }
        };
    }

    @Bean(name = "databaseLogger")
    public RequestLogger databaseRequestLogger() {
        return new DatabaseRequestLogger(repository);
    }
}
