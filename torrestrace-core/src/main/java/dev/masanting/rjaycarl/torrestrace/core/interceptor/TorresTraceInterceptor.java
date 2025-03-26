package dev.masanting.rjaycarl.torrestrace.core.interceptor;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Component
public class TorresTraceInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TorresTraceInterceptor.class);

    private final RequestLogger logger;

    @Autowired
    public TorresTraceInterceptor(@Value("${torrestrace.logger-type}") String loggerType,
                                  @Qualifier("databaseLogger") RequestLogger databaseLogger,
                                  @Qualifier("fileLogger") RequestLogger fileLogger) {

        if (loggerType.equals("database")) {
            this.logger = databaseLogger;
        } else {
            this.logger = fileLogger;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        List<String> SWAGGER_PATHS = List.of("/swagger-ui", "/api-docs", "/torrestrace");
        String requestUrl = request.getRequestURI();

        if (SWAGGER_PATHS.stream().anyMatch(requestUrl::contains)) return;

        HttpRequestLog log = HttpRequestLog.create(
                request.getMethod(),
                request.getRequestURI(),
                extractHeaders(request),
                extractBody(request)
        );

        logger.save(log);
    }

    private Map<String, String> extractHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(
                        Collectors.toMap(
                                name -> name,
                                request::getHeader
                        )
                );
    }

    private String extractBody(HttpServletRequest request) {
        if (!(request instanceof ContentCachingRequestWrapper cachingRequest)) {
            log.warn("Request is not wrapped with ContentCachingRequestWrapper!");
            return null;
        }

        byte[] contentAsByteArray = cachingRequest.getContentAsByteArray();

        if (contentAsByteArray.length == 0) {
            log.debug("Request body is empty");
            return null;
        }

        try {
            String encoding = request.getCharacterEncoding();
            String body = new String(contentAsByteArray,
                    encoding != null ? encoding : StandardCharsets.UTF_8.name());

            log.debug("Extracted request body: {}", body);
            return body;

        } catch (UnsupportedEncodingException e) {
            log.error("Failed to read cached request body", e);
            return null;
        }
    }


}
