package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class OperationNotAllowedException extends ApplicationException {
    private static final String ERROR_MESSAGE_PATTERN = "Operation not allowed cause %s!";

    private OperationNotAllowedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public static OperationNotAllowedException of(String cause) {
        return new OperationNotAllowedException(ERROR_MESSAGE_PATTERN.formatted(cause));
    }
}
