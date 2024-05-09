package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends ApplicationException {
    public WrongPasswordException() {
        super(HttpStatus.CONFLICT, "Wrong password!");
    }
}
