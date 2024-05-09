package com.lelar.database.dao;

import com.lelar.database.entity.PermissionEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface PermissionRepository extends ListCrudRepository<PermissionEntity, Long> {
}
