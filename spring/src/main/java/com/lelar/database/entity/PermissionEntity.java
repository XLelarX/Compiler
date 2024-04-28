package com.lelar.database.entity;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.lelar.database.entity.PermissionEntity.Names.DEFAULT_VALUE;
import static com.lelar.database.entity.PermissionEntity.Names.NAME;
import static com.lelar.database.entity.PermissionEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.PermissionEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(name = TABLE_NAME, sequence = SEQUENCE_NAME)
public class PermissionEntity extends IdentifierEntity {

    @Column(NAME)
    private String name;

//    @Column(ALLOWED)
//    private boolean allowed;

    @Column(DEFAULT_VALUE)
    private boolean defaultValue;

    public interface Names {
        String TABLE_NAME = "permissions";

        String SEQUENCE_NAME = "seq_pk_permission_id";

        String NAME = "name";
        String ALLOWED = "allowed";
        String DEFAULT_VALUE = "default_value";
    }
}
