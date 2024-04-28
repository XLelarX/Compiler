package com.lelar.database.repository;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.repository.api.PermissionRepository;
import com.lelar.database.repository.api.base.GetEntitiesRepository;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;
import com.lelar.database.repository.api.base.InsertAndUpdateWithRefsRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.lelar.database.entity.IdentifierEntity.Names.ID;
import static com.lelar.database.entity.PermissionEntity.Names.ALLOWED;
import static com.lelar.database.entity.PermissionEntity.Names.DEFAULT_VALUE;
import static com.lelar.database.entity.PermissionEntity.Names.NAME;
import static com.lelar.util.QueryHelper.sendCustomQuery;
import static com.lelar.util.QueryHelper.sendCustomUpdateQuery;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository, GetEntitiesRepository<PermissionEntity>, InsertAndUpdateRepository<PermissionEntity> {
    private static final String FIND_BY_USER_ID_QUERY_PARAM = "user_id";
    private static final String FIND_BY_USER_ID_QUERY = "select * from permissions left join permission_bindings on permissions.id = permission_bindings.permission_id where %s = ?".formatted(FIND_BY_USER_ID_QUERY_PARAM);

    private static final String INSERT_QUERY = "insert into permission_bindings values(next value for seq_pk_permission_binding_id, ?, ?, ?)";

    @Override
    public List<PermissionEntity> findByUserId(Long userId) {
        RowMapper<PermissionEntity> res = (resultSet, rowNum) -> {
            PermissionEntity permissionEntity = new PermissionEntity()
                .setName(resultSet.getString(NAME))
                .setDefaultValue(resultSet.getBoolean(ALLOWED));
            permissionEntity.setId(resultSet.getLong(ID));
            return permissionEntity;
        };

        return sendCustomQuery(FIND_BY_USER_ID_QUERY, Map.of(FIND_BY_USER_ID_QUERY_PARAM, userId), res);
    }

    @Override
    public void insertIntoBindingTable(Long permissionId, Long userId, boolean allowed) {
        sendCustomUpdateQuery(INSERT_QUERY, permissionId, userId, allowed);
    }

    @Override
    public RowMapper<PermissionEntity> getRowMapper() {
        return (resultSet, rowNum) -> {
            PermissionEntity permissionEntity = new PermissionEntity()
                .setName(resultSet.getString(NAME))
                .setDefaultValue(resultSet.getBoolean(DEFAULT_VALUE));
            permissionEntity.setId(resultSet.getLong(ID));
            return permissionEntity;
        };
    }
}
