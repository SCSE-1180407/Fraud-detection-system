package com.boringco.sentrax.exception;
/** Checked: the caller must handle bad user input. */
public class InvalidTransactionException extends Exception {
    public InvalidTransactionException(String message) { super(message); }
}
