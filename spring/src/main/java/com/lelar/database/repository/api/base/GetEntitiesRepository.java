package com.lelar.database.repository.api.base;

import com.lelar.database.entity.IdentifierEntity;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

import static com.lelar.util.QueryHelper.sendSelectQuery;

public interface GetEntitiesRepository<T extends IdentifierEntity> {

    default List<T> get(Class<T> clazz, Map<String, Object> params) {
        return sendSelectQuery(clazz, params, getRowMapper());
    }

    RowMapper<T> getRowMapper();

}
