package com.lelar.database.repository.api;

import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.repository.api.base.InsertAndUpdateRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentRepository extends InsertAndUpdateRepository<TournamentEntity> {
    List<TournamentEntity> findBetween(LocalDateTime startDate, LocalDateTime endDate);

}
