package com.lelar.enricher.api;

public interface Enricher<S, T> {

    void enrich(S source, T target);

}
