package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class StorePictureException extends ApplicationException {
    public StorePictureException() {
        super(HttpStatus.CONFLICT, "Picture not stored!");
    }
}
