package com.lelar.dto.register;

import com.lelar.dto.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String firstName;
    private String secondName;
    private String patronymic;
    private Gender gender;
    private LocalDateTime birthDate;
    private String username;
    private String password;
    private String email;
}
