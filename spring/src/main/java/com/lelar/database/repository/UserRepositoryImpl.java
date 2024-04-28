package com.lelar.database.repository;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.database.entity.UserEntity.Names;
import com.lelar.database.repository.api.UserRepository;
import com.lelar.database.repository.api.base.GetEntityRepository;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lelar.database.entity.IdentifierEntity.Names.ID;
import static com.lelar.util.QueryHelper.sendCustomQuery;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository, InsertAndUpdateRepository<UserEntity>, GetEntityRepository<UserEntity> {

    private static final String query = "select u.id, u.login_id, u.first_name, u.second_name, u.patronymic, GROUP_CONCAT(pb.allowed) as p_allowed, GROUP_CONCAT(p.name) as p_name from users as u left join permission_bindings as pb on u.id = pb.user_id left join permissions as p on pb.permission_id = p.id where u.id = ?";

    @Override
    public ResultSetExtractor<UserEntity> getResultSetExtractor() {
        return resultSet -> {
            if (!resultSet.next()) {
                return null;
            }

            UserEntity userEntity = new UserEntity()
                .setFirstName(resultSet.getString(Names.FIRST_NAME))
                .setSecondName(resultSet.getString(Names.SECOND_NAME))
                .setPatronymic(resultSet.getString(Names.PATRONYMIC));

            userEntity.setId(resultSet.getLong(ID));

            return userEntity;
        };
    }

    @Override
    public UserEntity findById(Long id) {
        ResultSetExtractor<UserEntity> res = resultSet -> {
            if (!resultSet.next()) {
                return null;
            }

            UserEntity userEntity = new UserEntity()
                .setFirstName(resultSet.getString(Names.FIRST_NAME))
                .setSecondName(resultSet.getString(Names.SECOND_NAME))
                .setPatronymic(resultSet.getString(Names.PATRONYMIC));

            userEntity.setId(resultSet.getLong(ID));

//            String[] allowedArr = resultSet.getString("p_allowed").split(",");
//            String[] names = resultSet.getString("p_name").split(",");
//
//            int arrLength = allowedArr.length;
//
//            userEntity.setPermissions(IntStream.range(0, arrLength).mapToObj(
//                it -> new PermissionEntity().setAllowed(allowedArr[it].equals("TRUE")).setName(names[it])
//            ).collect(Collectors.toList()));

            return userEntity;
        };

        return sendCustomQuery(query, Map.of("u.id", id), res);
    }
}
