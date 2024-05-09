package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PermissionEntity.Names.DEFAULT_VALUE;
import static com.lelar.database.entity.PermissionEntity.Names.NAME;
import static com.lelar.database.entity.PermissionEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class PermissionEntity implements Persistable<Long> {
    @Id
    private Long id;

    @Column(NAME)
    private String name;

    @Column(DEFAULT_VALUE)
    private boolean defaultValue;

    @Override
    public boolean isNew() {
        return true;
    }

    public interface Names {
        String TABLE_NAME = "PERMISSIONS";

        String NAME = "NAME";
        String ALLOWED = "allowed";
        String DEFAULT_VALUE = "DEFAULT_VALUE";
    }
}
