package com.lelar.database.repository;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.repository.api.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class LoginRepositoryImpl implements LoginRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public LoginEntity findByLogin(String userLogin) {
        System.out.println(userLogin);
        return jdbcTemplate.query("select id, login, password from logins where login = ?", this::mapRowToDataEntity, userLogin);
    }

    private LoginEntity mapRowToDataEntity(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        return new LoginEntity().setId(resultSet.getLong("id"))
            .setLogin(resultSet.getString("login"))
            .setPassword(resultSet.getString("password"));
    }
}
