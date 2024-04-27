package com.lelar.database.repository.api;

import com.lelar.database.entity.UserEntity;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;

public interface UserRepository extends InsertAndUpdateRepository<UserEntity> {
    UserEntity findById(Long id);
}
