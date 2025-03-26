package dev.masanting.rjaycarl.torrestrace.core.service;

import dev.masanting.rjaycarl.torrestrace.core.model.HttpReplayResult;

import java.net.URI;
import java.util.UUID;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public interface RequestReplayer {

    HttpReplayResult replay(UUID id, URI targetUrl);

}
