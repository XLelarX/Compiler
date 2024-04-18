package com.lelar.database.repository.api;

import com.lelar.database.entity.UserEntity;

public interface UserRepository {
    UserEntity findById(Long id);
}
