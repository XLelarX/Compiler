package com.lelar.session.storage;

import com.lelar.session.SessionData;
import com.lelar.session.storage.api.SessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
//TODO need to add caffeine cache
@Slf4j
public class SessionStorageImpl implements SessionStorage {

    private static final Map<String, SessionData> SESSION_DATA_MAP = new ConcurrentHashMap<>();

    @Override
    public SessionData pull(String sessionId) {
        return SESSION_DATA_MAP.getOrDefault(sessionId, null);
    }

    @Override
    public void put(String sessionId, SessionData sessionData) {
        SESSION_DATA_MAP.putIfAbsent(sessionId, sessionData);
        log.info("Session data field with ID {} {}", sessionId, sessionData);
    }

    @Override
    public void clean(String sessionId) {
        SESSION_DATA_MAP.remove(sessionId);
        log.info("Session data with ID {} filled", sessionId);
    }
}
