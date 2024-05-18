package com.lelar.database.entity;

import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PermissionEntity.Names.DEFAULT_VALUE;
import static com.lelar.database.entity.PermissionEntity.Names.NAME;
import static com.lelar.database.entity.PermissionEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class PermissionEntity extends IdentifierEntity {
    @Column(NAME)
    private String name;

    @Column(DEFAULT_VALUE)
    private boolean defaultValue;

    public interface Names {
        String TABLE_NAME = "PERMISSIONS";

        String NAME = "NAME";
        String DEFAULT_VALUE = "DEFAULT_VALUE";
    }
}
