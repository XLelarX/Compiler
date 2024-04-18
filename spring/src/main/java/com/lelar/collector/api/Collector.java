package com.lelar.collector.api;

import com.lelar.exception.ApplicationException;

public interface Collector<Q, P> {
    P collect(Q request) throws ApplicationException;
}
