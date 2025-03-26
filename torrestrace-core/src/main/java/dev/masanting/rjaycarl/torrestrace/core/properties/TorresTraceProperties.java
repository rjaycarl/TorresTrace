package dev.masanting.rjaycarl.torrestrace.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "torrestrace")
@Primary
public class TorresTraceProperties {

    private boolean enabled = true;
    private String loggerType = "file";                                 // file, db, in-memory, etc.
    private String filePath = "logs/torrestrace/request-logs.jsonl";    // path where logs are stored
    private long maxFileSizeMb = 10;                                    // Rotation trigger size (MB)
    private int maxBackupFiles = 5;                                     // How many rotated backups to keep

}
