package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PermissionBindingEntity.Names.ALLOWED;
import static com.lelar.database.entity.PermissionBindingEntity.Names.PERMISSION_ID;
import static com.lelar.database.entity.PermissionBindingEntity.Names.TABLE_NAME;
import static com.lelar.database.entity.PermissionBindingEntity.Names.USER_ID;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class PermissionBindingEntity {

    @Column(ALLOWED)
    private boolean allowed;

    @Column(PERMISSION_ID)
    private AggregateReference<PermissionEntity, Long> permissionId;

    @Column(USER_ID)
    private AggregateReference<UserEntity, Long> userId;

    public interface Names {
        String TABLE_NAME = "PERMISSION_BINDINGS";

        String ALLOWED = "ALLOWED";
        String PERMISSION_ID = "PERMISSION_ID";
        String USER_ID = "USER_ID";
    }
}
