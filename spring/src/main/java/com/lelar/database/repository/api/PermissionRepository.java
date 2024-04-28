package com.lelar.database.repository.api;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;

import java.util.List;

public interface PermissionRepository  {
    List<PermissionEntity> findByUserId(Long userId);

    void insertIntoBindingTable(Long permissionId, Long userId, boolean allowed);
}
