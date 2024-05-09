package com.lelar.exception;

import org.springframework.http.HttpStatus;

public class ClientAlreadyRegistered extends ApplicationException {
    public ClientAlreadyRegistered() {
        super(HttpStatus.CONFLICT, "Client already registered!");
    }
}
