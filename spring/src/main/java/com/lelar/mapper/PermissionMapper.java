package com.lelar.mapper;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.dto.login.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    @Mapping(source = "defaultValue", target = "allowed")
    Permission map(PermissionEntity entity);

}
