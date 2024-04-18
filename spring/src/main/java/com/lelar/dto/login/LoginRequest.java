package com.lelar.dto.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String password;
    private String username;
}
