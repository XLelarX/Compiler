package com.lelar.dto.login;

import lombok.Data;

import java.util.List;

@Data
public class UserData {
    private String firstName;
    private String secondName;
    private String patronymic;
    private List<SquadData> squadData;
}
