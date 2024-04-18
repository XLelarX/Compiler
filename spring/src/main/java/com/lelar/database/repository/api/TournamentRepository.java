package com.lelar.database.repository.api;

import com.lelar.database.entity.TournamentEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentRepository {
    List<TournamentEntity> findBetween(LocalDateTime startDate, LocalDateTime endDate);
}
