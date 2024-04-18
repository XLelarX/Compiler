package com.lelar.mapper;

import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TournamentMapper {
    TournamentMapper INSTANCE = Mappers.getMapper(TournamentMapper.class);

    Tournament map(TournamentEntity entity);

}
