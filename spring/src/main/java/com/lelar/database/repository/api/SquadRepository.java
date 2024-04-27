package com.lelar.database.repository.api;

import com.lelar.database.entity.SquadEntity;

import java.util.List;

public interface SquadRepository {
    List<SquadEntity> findByUserId(Long userId);
}
