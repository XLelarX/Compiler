package com.lelar.mapper;

import com.lelar.dto.login.LoginResponse;
import com.lelar.storage.session.SessionData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionDataMapper {
    SessionDataMapper INSTANCE = Mappers.getMapper(SessionDataMapper.class);

    SessionData map(LoginResponse response);
}
