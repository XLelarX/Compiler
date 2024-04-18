package com.lelar.database.repository.api;

import com.lelar.database.entity.PermissionEntity;

import java.util.List;

public interface PermissionRepository {
    List<PermissionEntity> findByUserId(Long userId);
}
