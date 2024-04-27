package com.lelar.database.repository;

import com.lelar.database.entity.SquadEntity;
import com.lelar.database.repository.api.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SquadRepositoryImpl implements SquadRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<SquadEntity> findByUserId(Long userId) {
        return jdbcTemplate.query("select name from squads left join squad_bindings on squads.id = squad_bindings.squad_id where user_id = ?", this::mapRowToDataEntity, userId);
    }

    private SquadEntity mapRowToDataEntity(ResultSet resultSet, int rowNum) throws SQLException {
        return new SquadEntity().setName(resultSet.getString("name"));
    }


}
