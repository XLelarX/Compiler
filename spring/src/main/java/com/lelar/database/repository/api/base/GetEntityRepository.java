package com.lelar.database.repository.api.base;

import com.lelar.database.entity.IdentifierEntity;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.Map;

import static com.lelar.util.QueryHelper.sendSelectQuery;

public interface GetEntityRepository<T extends IdentifierEntity> {

    default T get(Class<T> clazz, Map<String, Object> params) {
        return sendSelectQuery(clazz, params, getResultSetExtractor());
    }

    ResultSetExtractor<T> getResultSetExtractor();

}
