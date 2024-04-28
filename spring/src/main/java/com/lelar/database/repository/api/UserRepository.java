package com.lelar.database.repository.api;

import com.lelar.database.entity.UserEntity;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;

public interface UserRepository {
    UserEntity findById(Long id);
}
