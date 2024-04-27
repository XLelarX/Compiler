package com.lelar.database.repository.api.base;

import com.lelar.database.entity.IdentifierEntity;

import static com.lelar.util.QueryHelper.sendInsertQuery;
import static com.lelar.util.QueryHelper.sendUpdateQuery;

public interface InsertAndUpdateRepository<T extends IdentifierEntity> {

    default void insert(T object) {
        sendInsertQuery(object.getClass(), object);
    }

    default void update(T object) {
        sendUpdateQuery(object.getClass(), object);
    }

}
