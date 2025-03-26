package dev.masanting.rjaycarl.torrestrace.core.exception;

/**
 * @author R-Jay Carl Torres
 * @version 1.0.0
 * @since 2025-03-26
 */
public class ReplayException extends RuntimeException {

    public ReplayException(String message) {
        super(message);
    }

    public ReplayException(String message, Throwable cause) {
        super(message, cause);
    }

}
