package com.lelar.database.dao;

import com.lelar.database.entity.LoginEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface LoginRepository extends ListCrudRepository<LoginEntity, Long> {

    @Query("SELECT * FROM LOGINS WHERE LOGIN = :login")
    LoginEntity findByLogin(@Param("login") String login);
}
