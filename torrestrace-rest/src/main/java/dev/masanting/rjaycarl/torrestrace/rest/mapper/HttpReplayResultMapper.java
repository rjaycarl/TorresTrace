package dev.masanting.rjaycarl.torrestrace.rest.mapper;

import dev.masanting.rjaycarl.rest.model.HttpReplayResult;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public class HttpReplayResultMapper {

    public static HttpReplayResult toRest(dev.masanting.rjaycarl.torrestrace.core.model.HttpReplayResult coreResult) {
        HttpReplayResult restResult = new HttpReplayResult();
        restResult.setDurationMs(coreResult.durationMs());
        restResult.setResponseBody(coreResult.responseBody());
        restResult.setStatusCode(coreResult.statusCode());

        return restResult;
    }

    public static dev.masanting.rjaycarl.torrestrace.core.model.HttpReplayResult toCore(HttpReplayResult restResult) {
        return new dev.masanting.rjaycarl.torrestrace.core.model.HttpReplayResult(
                restResult.getStatusCode(),
                restResult.getResponseBody(),
                restResult.getDurationMs());
    }

}
