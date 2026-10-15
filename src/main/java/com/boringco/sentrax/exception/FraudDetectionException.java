package com.boringco.sentrax.exception;
/** Unchecked: wraps database or engine failures. */
public class FraudDetectionException extends RuntimeException {
    public FraudDetectionException(String message, Throwable cause) { super(message, cause); }
}
