package com.lelar.storage.session.api;

import com.lelar.storage.session.SessionData;

public interface SessionStorage {
    SessionData pull(String sessionId);

    void put(String sessionId, SessionData sessionData);

    void clean(String sessionId);
}
