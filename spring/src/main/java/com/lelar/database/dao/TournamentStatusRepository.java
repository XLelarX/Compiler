package com.lelar.database.dao;

import com.lelar.database.entity.TournamentStatusEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface TournamentStatusRepository extends ListCrudRepository<TournamentStatusEntity, Long> {

    @Query("SELECT * FROM TOURNAMENT_STATUS WHERE STATUS_KEY = :KEY")
    TournamentStatusEntity findByKey(@Param("KEY") String key);
}
