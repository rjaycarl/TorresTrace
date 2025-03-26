package dev.masanting.rjaycarl.torrestrace.storage.service;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import dev.masanting.rjaycarl.torrestrace.storage.entity.HttpRequestLogEntity;
import dev.masanting.rjaycarl.torrestrace.storage.repository.HttpRequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Service
public class DatabaseRequestLogger implements RequestLogger {

    private final HttpRequestLogRepository repository;

    @Autowired
    public DatabaseRequestLogger(HttpRequestLogRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public HttpRequestLog save(HttpRequestLog requestLog) {
        HttpRequestLogEntity entity = HttpRequestLogEntity.fromModel(requestLog);
        return repository.save(entity).toModel();
    }

    @Override
    @Transactional
    public List<HttpRequestLog> findAll() {
        return repository.findAll().stream().map(HttpRequestLogEntity::toModel).toList();
    }

    @Override
    @Transactional
    public Optional<HttpRequestLog> findById(UUID id) {
        return repository.findById(id).map(HttpRequestLogEntity::toModel);
    }

    @Override
    @Transactional
    public void deletedById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void clearAll() {
        repository.deleteAll();
    }
}
