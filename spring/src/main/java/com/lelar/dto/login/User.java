package com.lelar.dto.login;

import com.lelar.dto.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private String firstName;
    private String secondName;
    private String patronymic;
    private Gender gender;
    private LocalDateTime birthDate;
    private Long rating;
    private String email;
}
