package dev.masanting.rjaycarl.torrestrace.rest.controller;

import dev.masanting.rjaycarl.rest.api.HttpRequestLogsApi;
import dev.masanting.rjaycarl.rest.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import dev.masanting.rjaycarl.torrestrace.rest.mapper.HttpRequestLogMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@RestController
public class HttpRequestLogController implements HttpRequestLogsApi {

    private final RequestLogger logger;

    public HttpRequestLogController(@Value("${torrestrace.logger-type}") String loggerType,
                                    @Qualifier("databaseLogger") RequestLogger databaseLogger,
                                    @Qualifier("fileLogger") RequestLogger fileLogger) {
        if (loggerType.equals("database")) {
            this.logger = databaseLogger;
        } else {
            this.logger = fileLogger;
        }
    }

    @Override
    public ResponseEntity<Void> deleteLogById(UUID id) {
        logger.deletedById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<HttpRequestLog>> getAllLogs() {
        var coreLogs = logger.findAll();
        var restLogs = coreLogs.stream()
                .map(HttpRequestLogMapper::toRest)
                .toList();
        return ResponseEntity.ok(restLogs);
    }

    @Override
    public ResponseEntity<HttpRequestLog> getLogById(UUID id) {
        return logger.findById(id)
                .map(HttpRequestLogMapper::toRest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> logRequest(HttpRequestLog httpRequestLog) {
        var coreLog = HttpRequestLogMapper.toCore(httpRequestLog);
        logger.save(coreLog);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
