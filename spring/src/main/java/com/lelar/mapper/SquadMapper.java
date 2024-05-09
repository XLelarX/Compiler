package com.lelar.mapper;

import com.lelar.database.entity.SquadEntity;
import com.lelar.dto.login.Squad;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SquadMapper {
    SquadMapper INSTANCE = Mappers.getMapper(SquadMapper.class);

    default Set<Squad> mapSquads(List<SquadEntity> entities) {
        return entities.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

    Squad map(SquadEntity entity);

}
