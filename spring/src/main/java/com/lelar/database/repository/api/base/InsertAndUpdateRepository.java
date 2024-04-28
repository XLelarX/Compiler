package com.lelar.database.repository.api.base;

import com.lelar.database.entity.IdentifierEntity;

import static com.lelar.util.QueryHelper.sendInsertQuery;
import static com.lelar.util.QueryHelper.sendSeqQuery;
import static com.lelar.util.QueryHelper.sendUpdateQuery;

public interface InsertAndUpdateRepository<T extends IdentifierEntity> {

    default Long insert(T object) {
        return sendInsertQuery(object.getClass(), object);
    }

    default void update(T object) {
        sendUpdateQuery(object.getClass(), object);
    }

}
