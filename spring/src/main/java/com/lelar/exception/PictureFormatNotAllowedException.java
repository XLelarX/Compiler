package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class PictureFormatNotAllowedException extends ApplicationException {
    public PictureFormatNotAllowedException() {
        super(HttpStatus.CONFLICT, "Picture format not allowed!");
    }
}
