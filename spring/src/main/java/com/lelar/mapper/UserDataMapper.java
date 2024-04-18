package com.lelar.mapper;

import com.lelar.database.entity.SquadEntity;
import com.lelar.database.entity.UserEntity;
import com.lelar.dto.login.SquadData;
import com.lelar.dto.login.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserDataMapper {
    UserDataMapper INSTANCE = Mappers.getMapper(UserDataMapper.class);

    default UserData map(UserEntity userEntity, List<SquadEntity> squadEntities) {
        UserData userData = map(userEntity);
        List<SquadData> squadData = squadEntities.stream().map(this::map).collect(Collectors.toList());
        userData.setSquadData(squadData);
        return userData;
    }

    SquadData map(SquadEntity entity);

    UserData map(UserEntity entity);

}
