package com.lelar.service.update.api;

import com.lelar.exception.ApplicationException;

public interface UpdateService<Q> {
    boolean update(Q request) throws ApplicationException;
}
