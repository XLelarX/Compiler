package com.lelar.validator.api;

public interface Validator<T> {
    boolean validate(T object);
}
