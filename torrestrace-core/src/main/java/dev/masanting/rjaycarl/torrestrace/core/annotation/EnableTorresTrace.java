package dev.masanting.rjaycarl.torrestrace.core.annotation;

import dev.masanting.rjaycarl.torrestrace.core.TorresTraceCoreAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TorresTraceCoreAutoConfiguration.class)
public @interface EnableTorresTrace {
}
