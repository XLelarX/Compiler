package com.lelar.dto.login.register;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String firstName;
    private String secondName;
    private String patronymic;
    private LocalDateTime birthDate;
    private String username;
    private String password;
}
