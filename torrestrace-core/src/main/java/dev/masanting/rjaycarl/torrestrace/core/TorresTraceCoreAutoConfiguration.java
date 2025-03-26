package dev.masanting.rjaycarl.torrestrace.core;

import dev.masanting.rjaycarl.torrestrace.core.properties.TorresTraceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Configuration
@EnableConfigurationProperties(TorresTraceProperties.class)
public class TorresTraceCoreAutoConfiguration {
}
