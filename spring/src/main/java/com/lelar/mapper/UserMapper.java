package com.lelar.mapper;

import com.lelar.database.entity.UserEntity;
import com.lelar.dto.login.User;
import com.lelar.dto.login.register.RegisterRequest;
import com.lelar.instance.UserInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper extends CommonMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User map(UserEntity entity);

    UserInstance mapToInstance(UserEntity entity);

    @Mapping(target = "login.password", source = "password")
    @Mapping(target = "login.login", source = "username")
    UserEntity map(RegisterRequest entity);

}
