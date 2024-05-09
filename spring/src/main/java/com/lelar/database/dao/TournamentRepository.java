package com.lelar.database.dao;

import com.lelar.database.entity.TournamentEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentRepository extends CrudRepository<TournamentEntity, Long> {

    @Query("SELECT * FROM TOURNAMENTS WHERE START_DATE BETWEEN :BEGIN_DATE AND :END_DATE")
    List<TournamentEntity> findBetween(@Param("BEGIN_DATE") LocalDateTime startDate, @Param("END_DATE") LocalDateTime endDate);

}
