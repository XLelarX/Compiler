package com.lelar.database.repository.api;

import com.lelar.database.entity.LoginEntity;

public interface LoginRepository {
    LoginEntity findByLogin(String userLogin);
}
