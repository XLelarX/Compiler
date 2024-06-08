package com.lelar.processor.update.api;

import com.lelar.exception.ApplicationException;

public interface UpdateDataProcessor<Q> {
    Long update(Q request) throws ApplicationException;
}
