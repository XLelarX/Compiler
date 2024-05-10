package com.lelar.service.update.api;

import com.lelar.exception.ApplicationException;

public interface UpdateDataService<Q> {
    void update(Q request) throws ApplicationException;
}
