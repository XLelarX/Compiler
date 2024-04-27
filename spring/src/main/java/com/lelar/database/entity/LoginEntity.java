package com.lelar.database.entity;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.lelar.database.entity.LoginEntity.Names.LOGIN;
import static com.lelar.database.entity.LoginEntity.Names.PASSWORD;
import static com.lelar.database.entity.LoginEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.LoginEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(name = TABLE_NAME, sequence = SEQUENCE_NAME)
public class LoginEntity extends IdentifierEntity {

    @Column(LOGIN)
    private String login;

    @Column(PASSWORD)
    private String password;

    public interface Names {
        String TABLE_NAME = "logins";
        String SEQUENCE_NAME = "seq_pk_login_id";

        String LOGIN = "login";
        String PASSWORD = "password";
    }
}
