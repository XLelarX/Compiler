package com.lelar.database.repository.api.base;

import com.lelar.database.entity.IdentifierEntity;

import java.util.Map;

import static com.lelar.util.QueryHelper.sendInsertQuery;
import static com.lelar.util.QueryHelper.sendUpdateQuery;

public interface InsertAndUpdateWithRefsRepository<T extends IdentifierEntity> {

    default void insert(T object, Map<String, Object> alsoParams) {
        sendInsertQuery(object.getClass(), object);
        alsoInsert(object, alsoParams);
    }

    void alsoInsert(T object, Map<String, Object> alsoParams);

    default void update(T object) {
        sendUpdateQuery(object.getClass(), object);
    }

}
