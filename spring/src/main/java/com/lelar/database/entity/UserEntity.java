package com.lelar.database.entity;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.lelar.database.entity.UserEntity.Names.FIRST_NAME;
import static com.lelar.database.entity.UserEntity.Names.LOGIN_ID;
import static com.lelar.database.entity.UserEntity.Names.PATRONYMIC;
import static com.lelar.database.entity.UserEntity.Names.SECOND_NAME;
import static com.lelar.database.entity.UserEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.UserEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(name = TABLE_NAME, sequence = SEQUENCE_NAME)
public class UserEntity extends IdentifierEntity {

    @Column(FIRST_NAME)
    private String firstName;

    @Column(SECOND_NAME)
    private String secondName;

    @Column(PATRONYMIC)
    private String patronymic;

    @Column(LOGIN_ID)
    private Long loginId;

    public interface Names {
        String TABLE_NAME = "users";

        String SEQUENCE_NAME = "seq_pk_user_id";

        String FIRST_NAME = "first_name";
        String SECOND_NAME = "second_name";
        String PATRONYMIC = "patronymic";
        String LOGIN_ID = "login_id";
    }

}