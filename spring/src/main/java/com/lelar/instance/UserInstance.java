package com.lelar.instance;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInstance {
    private String firstName;
    private String secondName;
    private String patronymic;
    private String gender;
    private LocalDateTime birthDate;
    private Long rating;
}
