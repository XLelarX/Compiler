package com.lelar.storage.session;

import com.lelar.exception.OperationNotAllowedException;
import com.lelar.storage.session.api.SessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SessionStorageImpl implements SessionStorage {

    private static final Map<String, SessionData> SESSION_DATA_MAP = new ConcurrentHashMap<>();
    private static final String SESSION_DATA_IS_EMPTY_ERROR_MESSAGE = "Session data is empty";
    private static final String SESSION_DATA_TOO_OLD_ERROR_MESSAGE = "Session data too old";

    // Session data TTL 15 minutes
    private static final long TTL = 900000;

    @Override
    public SessionData pull(String sessionId) {
        SessionData sessionData;
        sessionData = SESSION_DATA_MAP.get(sessionId);
        if (sessionData == null) {
            throw OperationNotAllowedException.of(SESSION_DATA_IS_EMPTY_ERROR_MESSAGE);
        }

        if (System.currentTimeMillis() - sessionData.getTtl() > TTL) {
            clean(sessionId);
            throw OperationNotAllowedException.of(SESSION_DATA_TOO_OLD_ERROR_MESSAGE);
        }

        return sessionData;
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
