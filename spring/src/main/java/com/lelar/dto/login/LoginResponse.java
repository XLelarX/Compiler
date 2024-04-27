package com.lelar.dto.login;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LoginResponse {
    private List<Permission> permissions;
    private UserData userData;
}
