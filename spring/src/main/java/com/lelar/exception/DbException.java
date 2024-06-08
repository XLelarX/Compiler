package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class DbException extends ApplicationException {
    private static final String ERROR_MESSAGE_PATTERN = "Error during working with table %s!";

    DbException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public static DbException of(String tableName) {
        return new DbException(ERROR_MESSAGE_PATTERN.formatted(tableName));
    }
}
