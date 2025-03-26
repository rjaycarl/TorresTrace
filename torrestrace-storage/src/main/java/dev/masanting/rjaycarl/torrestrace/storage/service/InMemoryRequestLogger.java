package dev.masanting.rjaycarl.torrestrace.storage.service;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Deprecated
@Component
public class InMemoryRequestLogger implements RequestLogger {

    private static final Logger log = LoggerFactory.getLogger(InMemoryRequestLogger.class);

    private final Map<UUID, HttpRequestLog> logStore = new ConcurrentHashMap<>();

    @Override
    public HttpRequestLog save(HttpRequestLog requestLog) {
        logStore.put(requestLog.id(), requestLog);
        log.info("Logged request: {}", requestLog.id());
        return requestLog;
    }

    @Override
    public List<HttpRequestLog> findAll() {
        return new ArrayList<>(logStore.values());
    }

    @Override
    public Optional<HttpRequestLog> findById(UUID id) {
        return Optional.ofNullable(logStore.get(id));
    }

    @Override
    public void deletedById(UUID id) {
        logStore.remove(id);
        log.info("Deleted request: {}", id);
    }

    @Override
    public void clearAll() {
        logStore.clear();
        log.info("Cleared all logs.");
    }
}
