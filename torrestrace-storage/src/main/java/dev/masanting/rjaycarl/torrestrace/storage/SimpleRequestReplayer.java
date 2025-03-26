package dev.masanting.rjaycarl.torrestrace.storage;

import dev.masanting.rjaycarl.torrestrace.core.exception.ReplayException;
import dev.masanting.rjaycarl.torrestrace.core.model.HttpReplayResult;
import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestReplayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Component
public class SimpleRequestReplayer implements RequestReplayer {

    private static final Logger log = LoggerFactory.getLogger(SimpleRequestReplayer.class);

    private final RequestLogger logger;
    private final HttpClient httpClient;

    public SimpleRequestReplayer(@Qualifier("databaseLogger") RequestLogger logger) {
        this.logger = logger;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public HttpReplayResult replay(UUID id, URI targetUrl) {
        HttpRequestLog requestLog = logger.findById(id)
                .orElseThrow(() -> new ReplayException("No log found with ID: " + id));

        try {
            String rawUrl = (targetUrl != null) ? targetUrl.toString() : requestLog.url();
            String fixedUrl = (targetUrl != null) ? rawUrl : fixUrl(rawUrl);

            log.info("Replaying request to URL: {}", fixedUrl);
            log.info("Replaying with body: {}", requestLog.body());

            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(fixedUrl))
                    .method(requestLog.method(), HttpRequest.BodyPublishers.ofString(
                            requestLog.body() != null ? requestLog.body() : ""))
                    .timeout(Duration.ofSeconds(10));

            // Restricted headers you should NOT set manually
            Set<String> restrictedHeaders = Set.of(
                    "content-length", "host", "connection", "expect", "upgrade"
            );

            // Filter out restricted headers
            requestLog.headers().forEach((name, value) -> {
                if (!restrictedHeaders.contains(name.toLowerCase())) {
                    builder.header(name, value);
                } else {
                    log.debug("Skipping restricted header: {}", name);
                }
            });

            long start = System.nanoTime();
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
            long duration = (System.nanoTime() - start) / 1_000_000;

            log.info("Replayed request {} with status {}", id, response.statusCode());

            return new HttpReplayResult(response.statusCode(), response.body(), duration);
        } catch (Exception e) {
            log.error("Replay failed for request ID: {}", id, e);
            throw new ReplayException("Failed to replay request: " + e.getMessage(), e);
        }
    }

    private String fixUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new ReplayException("Invalid URL: URL is blank or null");
        }

        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }

        String baseUrl = "http://localhost:9002";

        return (url.startsWith("/")) ? (baseUrl + url) : (baseUrl + "/" + url);
    }

}
