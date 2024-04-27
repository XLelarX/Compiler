package com.lelar.service.get.api;

import com.lelar.exception.ApplicationException;

public interface GetService<Q, P> {
    P get(Q request) throws ApplicationException;
}
