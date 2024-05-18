package com.lelar.util;

import lombok.Data;

@Data
public class PrimitiveArrayWrapper {
    private byte[] byteArray;

    public PrimitiveArrayWrapper(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
