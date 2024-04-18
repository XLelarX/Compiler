package com.lelar.database.repository;

import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.repository.api.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TournamentRepositoryImpl implements TournamentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TournamentEntity> findBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jdbcTemplate.query("select t.name as tournament_name, start_date, end_date, gender_type, address, s.name as squad, j.name as opponent_squad from tournament as t left join squads as s on s.id= t.squad_id left join squads as j on j.id=t.opponents_squad_id where start_date between ? and ?", this::mapRowToDataEntity, startDate, endDate);
    }

    private TournamentEntity mapRowToDataEntity(ResultSet resultSet, int rowNum) throws SQLException {
        return new TournamentEntity()
            .setTournamentName(resultSet.getString("tournament_name"))
            .setStartDate(resultSet.getTimestamp("start_date"))
            .setEndDate(resultSet.getTimestamp("end_date"))
            .setGenderType(resultSet.getString("gender_type"))
            .setAddress(resultSet.getString("address"))
            .setSquadName(resultSet.getString("squad"))
            .setOpponentSquadName(resultSet.getString("opponent_squad"));
    }


}
