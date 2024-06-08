package com.lelar.dto.login;

import lombok.Data;

@Data
public class Permission {
    private String name;
    private String permissionKey;
    private Boolean allowed;
}
