package com.lelar.mapper;

import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TournamentMapper {
    TournamentMapper INSTANCE = Mappers.getMapper(TournamentMapper.class);

    default Set<Tournament> map(List<TournamentEntity> entity, Map<TournamentEntity, Set<PictureEntity>> pictures) {
        return entity.stream()
            .map(it -> mapTournamentWithInsides(pictures, it))
            .collect(Collectors.toSet());
    }

    default Tournament mapTournamentWithInsides(Map<TournamentEntity, Set<PictureEntity>> pictures, TournamentEntity it) {
        Tournament map = map(it);
        if (!CollectionUtils.isEmpty(pictures)) {
            map.setPictures(pictures.getOrDefault(it, Set.of()).stream().map(this::map).collect(Collectors.toSet()));
        }
        return map;
    }

    Tournament map(TournamentEntity entity);

    Picture map(PictureEntity entity);
}
