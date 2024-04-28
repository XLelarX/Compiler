package com.lelar.dto.login;

import lombok.Data;

@Data
public class RegisterRequest {
    private String password;
    private String username;
    private String firstName;
    private String secondName;
    private String patronymic;
}
