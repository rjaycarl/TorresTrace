package dev.masanting.rjaycarl.torrestrace.rest.mapper;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpRequestLog;

import java.time.ZoneId;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public class HttpRequestLogMapper {

    public static HttpRequestLog toCore(dev.masanting.rjaycarl.rest.model.HttpRequestLog restLog) {
        return new HttpRequestLog(
                UUID.randomUUID(),
                restLog.getMethod(),
                restLog.getUrl(),
                restLog.getHeaders(),
                restLog.getBody(),
                restLog.getTimestamp().toInstant()
        );
    }

    public static dev.masanting.rjaycarl.rest.model.HttpRequestLog toRest(HttpRequestLog coreLog) {
        var restLog = new dev.masanting.rjaycarl.rest.model.HttpRequestLog();
        restLog.id(coreLog.id());
        restLog.setMethod(coreLog.method());
        restLog.setUrl(coreLog.url());
        restLog.setHeaders(coreLog.headers());
        restLog.setBody(coreLog.body());
        restLog.setTimestamp(coreLog.timestamp().atZone(ZoneId.systemDefault()).toOffsetDateTime());

        return restLog;
    }

}
