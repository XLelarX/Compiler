package com.lelar.dto.login;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class LoginResponse {
    private User user;
    private Set<Squad> squads;
    private Set<Permission> permissions;
}
