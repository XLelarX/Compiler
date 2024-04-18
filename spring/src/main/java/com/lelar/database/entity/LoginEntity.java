package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginEntity {
    private Long id;
    private String login;
    private String password;
}
