package com.lelar.mapper;

import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.picture.PictureData;
import com.lelar.dto.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TournamentMapper {
    TournamentMapper INSTANCE = Mappers.getMapper(TournamentMapper.class);

    default Tournament map(TournamentEntity entity, List<PictureEntity> pictures) {
        Tournament tournament = map(entity);
        tournament.setPictures(pictures.stream().map(this::map).collect(Collectors.toList()));
        return tournament;
    }

    Tournament map(TournamentEntity entity);

    PictureData map(PictureEntity entity);
}
