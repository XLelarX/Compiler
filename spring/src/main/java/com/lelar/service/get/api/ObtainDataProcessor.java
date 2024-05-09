package com.lelar.service.get.api;

import com.lelar.exception.ApplicationException;

public interface ObtainDataProcessor<Q, P> {
    P process(Q request) throws ApplicationException;
}
