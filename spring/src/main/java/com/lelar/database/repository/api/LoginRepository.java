package com.lelar.database.repository.api;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;

public interface LoginRepository extends InsertAndUpdateRepository<LoginEntity> {
    LoginEntity findByLogin(String userLogin);
}
