package com.lelar.mapper;

import com.lelar.database.entity.PermissionEntity;
import com.lelar.database.entity.PermissionBindingEntity;
import com.lelar.dto.login.Permission;
import org.apache.commons.lang3.tuple.Pair;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface PermissionMapper extends CommonMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    default Set<Permission> mapPermissions(List<PermissionEntity> entities, Map<Long, Boolean> permissionsAdditionalData) {
        return entities.stream()
            .map(it -> Pair.of(it, permissionsAdditionalData.get(it.getId())))
            .map(it -> map(it.getLeft(), it.getRight()))
            .collect(Collectors.toSet());
    }

    @Mapping(target = "allowed", expression = "java(allowed)")
    Permission map(PermissionEntity entity, @Context Boolean allowed);

    default Set<Permission> mapPermissions(List<PermissionEntity> entities) {
        return entities.stream()
            .map(this::map)
            .collect(Collectors.toSet());
    }

    @Mapping(target = "allowed", source = "defaultValue")
    Permission map(PermissionEntity entity);

    @Mapping(target = "allowed", source = "defaultValue")
    @Mapping(target = "permissionId", source = "id")
    PermissionBindingEntity mapRef(PermissionEntity entity);

}
