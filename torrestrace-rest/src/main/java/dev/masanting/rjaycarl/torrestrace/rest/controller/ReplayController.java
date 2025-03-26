package dev.masanting.rjaycarl.torrestrace.rest.controller;

import dev.masanting.rjaycarl.rest.api.ReplayRequestsApi;
import dev.masanting.rjaycarl.rest.model.HttpReplayResult;
import dev.masanting.rjaycarl.torrestrace.core.service.RequestReplayer;
import dev.masanting.rjaycarl.torrestrace.rest.mapper.HttpReplayResultMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@RestController
public class ReplayController implements ReplayRequestsApi {
    private final RequestReplayer replayer;

    public ReplayController(RequestReplayer replayer) {
        this.replayer = replayer;
    }

    @Override
    public ResponseEntity<HttpReplayResult> replayRequest(UUID id, URI targetUrl) {
        var result = replayer.replay(id, targetUrl);
        var replayResult = HttpReplayResultMapper.toRest(result);

        return ResponseEntity.ok(replayResult);
    }
}
