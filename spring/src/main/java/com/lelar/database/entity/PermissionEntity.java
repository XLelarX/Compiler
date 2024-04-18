package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PermissionEntity {
    private Long id;
    private String name;
    private boolean allowed;
}
