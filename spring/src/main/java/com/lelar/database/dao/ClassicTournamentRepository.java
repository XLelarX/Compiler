package com.lelar.database.dao;

import com.lelar.database.entity.ClassicTournamentEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassicTournamentRepository extends ListCrudRepository<ClassicTournamentEntity, Long> {

    @Query("SELECT * FROM CLASSIC_TOURNAMENTS WHERE START_DATE BETWEEN :BEGIN_DATE AND :END_DATE AND GENDER = :GENDER")
    List<ClassicTournamentEntity> findByDateAndGender(
        @Param("BEGIN_DATE") LocalDateTime startDate,
        @Param("END_DATE") LocalDateTime endDate,
        @Param("GENDER") String gender
    );

}
