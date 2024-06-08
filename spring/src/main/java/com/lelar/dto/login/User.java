package com.lelar.dto.login;

import com.lelar.dto.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
    private String firstName;
    private String secondName;
    private String patronymic;
    private Gender gender;
    private LocalDateTime birthDate;
    private Long rating;
}
