package com.lelar.database.repository.api;

import com.lelar.database.entity.PictureEntity;

import java.util.List;

public interface PictureRepository {
    List<PictureEntity> findByTournamentId(Long tournamentIds);
}
