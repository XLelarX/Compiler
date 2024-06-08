package com.lelar.exception;

public class RecordInDbNotFoundException extends DbException {
    private static final String ERROR_MESSAGE_PATTERN = "Record not found in table %s!";

    private RecordInDbNotFoundException(String message) {
        super(message);
    }

    public static RecordInDbNotFoundException of(String tableName) {
        return new RecordInDbNotFoundException(ERROR_MESSAGE_PATTERN.formatted(tableName));
    }
}
