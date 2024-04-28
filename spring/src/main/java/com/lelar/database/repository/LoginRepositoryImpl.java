package com.lelar.database.repository;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.repository.api.base.GetEntityRepository;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepositoryImpl implements InsertAndUpdateRepository<LoginEntity>, GetEntityRepository<LoginEntity> {

    @Override
    public ResultSetExtractor<LoginEntity> getResultSetExtractor() {
        return resultSet -> {
            if (!resultSet.next()) {
                return null;
            }

            LoginEntity loginEntity = new LoginEntity()
                .setLogin(resultSet.getString("login"))
                .setPassword(resultSet.getString("password"));

            loginEntity.setId(resultSet.getLong("id"));
            return loginEntity;
        };
    }

}
