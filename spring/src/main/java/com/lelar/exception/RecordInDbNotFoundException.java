package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class RecordInDbNotFoundException extends ApplicationException {
    private static final String ERROR_MESSAGE_PATTERN = "Record not found in table %s!";

    private RecordInDbNotFoundException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public static RecordInDbNotFoundException of(String tableName) {
        return new RecordInDbNotFoundException(String.format(ERROR_MESSAGE_PATTERN, tableName));
    }
}
