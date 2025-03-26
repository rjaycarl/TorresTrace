package dev.masanting.rjaycarl.torrestrace.core.service;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public interface RequestLogger {

    HttpRequestLog save(HttpRequestLog requestLog);

    List<HttpRequestLog> findAll();

    Optional<HttpRequestLog> findById(UUID id);

    void deletedById(UUID id);

    void clearAll();

}
