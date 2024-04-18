package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEntity {
    private Long id;
    private String firstName;
    private String secondName;
    private String patronymic;

    public interface Names {
        String TABLE_NAME = "users";

        String ID = "id";
        String FIRST_NAME = "first_name";
        String SECOND_NAME = "second_name";
        String PATRONYMIC = "patronymic";
        String LOGIN_ID = "login_id";
    }
}