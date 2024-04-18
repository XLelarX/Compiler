package com.lelar.database.repository;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.repository.api.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {

    private final JdbcTemplate jdbcTemplate;

    private PermissionEntity mapRowToDataEntity(ResultSet resultSet, int rowNum) throws SQLException {
        return new PermissionEntity()
            .setName(resultSet.getString("name"))
            .setAllowed(resultSet.getBoolean("allowed"));
    }

    @Override
    public List<PermissionEntity> findByUserId(Long userId) {
        return jdbcTemplate.query("select permissions.name, allowed from permissions left join permission_bindings on permissions.id = permission_bindings.permission_id where user_id = ?", this::mapRowToDataEntity, userId);
    }
}
