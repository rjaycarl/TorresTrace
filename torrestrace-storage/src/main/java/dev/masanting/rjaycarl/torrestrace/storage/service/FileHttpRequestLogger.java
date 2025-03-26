package dev.masanting.rjaycarl.torrestrace.storage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.properties.TorresTraceProperties;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public class FileHttpRequestLogger implements RequestLogger {

    private static final Logger log = LoggerFactory.getLogger(FileHttpRequestLogger.class);

    private final TorresTraceProperties properties;
    private final ObjectMapper objectMapper;
    private final Path logFilePath;

    @Autowired
    public FileHttpRequestLogger(TorresTraceProperties properties, ObjectMapper objectMapper) throws IOException {
        this.properties = properties;
        this.objectMapper = objectMapper;

        this.logFilePath = Paths.get(properties.getFilePath());

        if (!Files.exists(logFilePath)) {
            Files.createDirectories(logFilePath.getParent());
            Files.createFile(logFilePath);
            log.info("Created log file at {}", logFilePath);
        }
    }

    @Override
    public synchronized HttpRequestLog save(HttpRequestLog requestLog) {
        rotateLogIfNeeded();

        try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardOpenOption.APPEND)) {
            writer.write(requestLog.toJson());
            writer.newLine();
            log.info("Saved log: {}", requestLog.id());
            return requestLog;
        } catch (IOException e) {
            log.error("Failed to write log file", e);
            throw new RuntimeException("Failed to write log file", e);
        }
    }

    private void rotateLogIfNeeded() {
        try {
            long maxFileSizeBytes = properties.getMaxFileSizeMb() * 1024 * 1024;
            long currentSize = Files.size(logFilePath);

            if (currentSize >= maxFileSizeBytes) {
                rotateFiles();
            }
        } catch (IOException e) {
            log.error("Failed to check log file size", e);
        }
    }

    private void rotateFiles() {
        try {
            // delete oldest backup if exceeds max
            Path oldestBackup = Paths.get(logFilePath + "." + properties.getMaxBackupFiles());
            if (Files.exists(oldestBackup)) {
                Files.delete(oldestBackup);
            }

            // shift backups (rotate backwards)
            for (int i = properties.getMaxBackupFiles() - 1; i >= 1; i--) {
                Path src = Paths.get(logFilePath + "." + i);
                Path dest = Paths.get(logFilePath + "." + (i + 1));

                if (Files.exists(src)) {
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // move current file to .1
            Path firstBackup = Paths.get(logFilePath + ".1");
            Files.move(logFilePath, firstBackup, StandardCopyOption.REPLACE_EXISTING);

            // create new empty log file
            Files.createFile(logFilePath);

            log.info("Log rotation completed. Archived {}", firstBackup);
        } catch (IOException e) {
            log.error("Failed during log rotation", e);
        }
    }

    @Override
    public List<HttpRequestLog> findAll() {
        try (Stream<String> lines = Files.lines(logFilePath)) {
            return lines.map(line -> {
                        try {
                            return objectMapper.readValue(line, HttpRequestLog.class);
                        } catch (JsonProcessingException e) {
                            log.error("Error reading log line", e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to read log file", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<HttpRequestLog> findById(UUID id) {
        return findAll().stream()
                .filter(log -> log.id().equals(id))
                .findFirst();
    }

    @Override
    public void deletedById(UUID id) {
        List<HttpRequestLog> logs = findAll().stream()
                .filter(log -> !log.id().equals(id))
                .collect(Collectors.toList());
        overwriteFile(logs);
    }

    @Override
    public void clearAll() {
        overwriteFile(Collections.emptyList());
    }

    private synchronized void overwriteFile(List<HttpRequestLog> logs) {
        try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (HttpRequestLog logEntry : logs) {
                writer.write(logEntry.toJson());
                writer.newLine();
            }
            log.info("Log file overwritten. {} entries written", logs.size());
        } catch (IOException e) {
            log.error("Failed to overwrite log file", e);
        }
    }
}
