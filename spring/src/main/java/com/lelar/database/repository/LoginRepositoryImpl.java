package com.lelar.database.repository;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.repository.api.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.lelar.util.QueryHelper.sendInsertQuery;

@Repository
@RequiredArgsConstructor
public class LoginRepositoryImpl implements LoginRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public LoginEntity findByLogin(String userLogin) {
        return jdbcTemplate.query("select id, login, password from logins where login = ?", this::mapRowToDataEntity, userLogin);
    }


    private LoginEntity mapRowToDataEntity(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        LoginEntity loginEntity = new LoginEntity()
            .setLogin(resultSet.getString("login"))
            .setPassword(resultSet.getString("password"));

        loginEntity.setId(resultSet.getLong("id"));
        return loginEntity;
    }
}
