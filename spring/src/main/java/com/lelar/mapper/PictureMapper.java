package com.lelar.mapper;

import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.dto.picture.Picture;
import com.lelar.instance.PictureInstance;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Map;

@Mapper
public interface PictureMapper {
    PictureMapper INSTANCE = Mappers.getMapper(PictureMapper.class);

    Picture map(PictureEntity entity);

    @Mapping(target = "formatId", source = "format", qualifiedByName = "mapFormat")
    PictureEntity map(Picture picture, @Context Map<String, Long> formats);

    @Named("mapFormat")
    default AggregateReference<PictureFormatEntity, Long> mapFormat(String format, @Context Map<String, Long> formats) {
        return AggregateReference.to(formats.get(format));
    }

    PictureInstance mapToInstance(PictureEntity entity);

}
