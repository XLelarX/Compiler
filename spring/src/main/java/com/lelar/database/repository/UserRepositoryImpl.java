package com.lelar.database.repository;

import com.lelar.database.entity.UserEntity;
import com.lelar.database.entity.UserEntity.Names;
import com.lelar.database.repository.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final static String FIND_BY_ID_REQUEST = ("select * from %s where %s = ?")
        .formatted( Names.TABLE_NAME, Names.LOGIN_ID);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_REQUEST, this::mapRowToDataEntity, id);
    }

    private UserEntity mapRowToDataEntity(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        return new UserEntity()
            .setId(resultSet.getLong(Names.ID))
            .setFirstName(resultSet.getString(Names.FIRST_NAME))
            .setSecondName(resultSet.getString(Names.SECOND_NAME))
            .setPatronymic(resultSet.getString(Names.PATRONYMIC));
    }
}
