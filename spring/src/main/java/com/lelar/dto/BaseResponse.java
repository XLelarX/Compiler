package com.lelar.dto;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private boolean success;
    private String error;
    private T body;

    private BaseResponse(boolean success) {
        this.success = success;
    }

    private BaseResponse(boolean success, T body) {
        this(success);
        this.body = body;
    }

    private BaseResponse(boolean success, String error) {
        this(success);
        this.error = error;
    }

    public static <T> BaseResponse<T> successResponse(T body) {
        return new BaseResponse<>(true, body);
    }

    public static <T> BaseResponse<T> emptySuccessResponse() {
        return new BaseResponse<>(true, null);
    }

    public static <T> BaseResponse<T> failureResponse(String error) {
        return new BaseResponse<>(false, error);
    }

}
