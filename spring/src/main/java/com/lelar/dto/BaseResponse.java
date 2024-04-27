package com.lelar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T> {
    private boolean success;
    private T body;
}
