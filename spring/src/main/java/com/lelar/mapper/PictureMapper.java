package com.lelar.mapper;

import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.Tournament;
import com.lelar.dto.tournament.update.UpdateTournamentRequest;
import com.lelar.instance.PictureInstance;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface PictureMapper {
    PictureMapper INSTANCE = Mappers.getMapper(PictureMapper.class);

//    default Set<Tournament> map(List<TournamentEntity> entity, Map<TournamentEntity, Set<PictureEntity>> pictures) {
//        return entity.stream()
//            .map(it -> mapTournamentWithInsides(pictures, it))
//            .collect(Collectors.toSet());
//    }
//
//    default Tournament mapTournamentWithInsides(Map<TournamentEntity, Set<PictureEntity>> pictures, TournamentEntity it) {
//        Tournament map = map(it);
//        if (!CollectionUtils.isEmpty(pictures)) {
//            map.setPictures(pictures.getOrDefault(it, Set.of()).stream().map(this::map).collect(Collectors.toSet()));
//        }
//        return map;
//    }
//
//    Tournament map(TournamentEntity entity);
//
//    TournamentEntity map(UpdateTournamentRequest request);

    Picture map(PictureEntity entity);

    @Mapping(target = "formatId", source = "format", qualifiedByName = "mapFormat")
    PictureEntity map(Picture picture, @Context Map<String, Long> formats);

    @Named("mapFormat")
    default AggregateReference<PictureFormatEntity, Long> mapFormat(String format, @Context Map<String, Long> formats) {
        return AggregateReference.to(formats.get(format));
    }

    PictureInstance mapToInstance(PictureEntity entity);

}
