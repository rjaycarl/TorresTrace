package dev.masanting.rjaycarl.torrestrace.core.service;

import dev.masanting.rjaycarl.torrestrace.core.properties.TorresTraceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public class TorresTraceService {

    private static final Logger logger = LoggerFactory.getLogger(TorresTraceService.class);
    private final TorresTraceProperties properties;

    public TorresTraceService(TorresTraceProperties properties) {
        this.properties = properties;
        logger.info("TorresTraceService initialized with: " +
                        "enabled={}, loggerType={}, filePath={}, maxFileSizeMb={}, maxBackupFiles={}",
                properties.isEnabled(), properties.getLoggerType(), properties.getFilePath(),
                properties.getMaxFileSizeMb(), properties.getMaxBackupFiles());

    }

}
