package com.lelar.session.storage.api;

import com.lelar.session.SessionData;

public interface SessionStorage {
    SessionData pull(String sessionId);

    void put(String sessionId, SessionData sessionData);

    void clean(String sessionId);
}
