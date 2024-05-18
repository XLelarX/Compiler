package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.LoginEntity.Names.LOGIN;
import static com.lelar.database.entity.LoginEntity.Names.PASSWORD;
import static com.lelar.database.entity.LoginEntity.Names.TABLE_NAME;
import static com.lelar.database.entity.LoginEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.LoginEntity.Names.USER_ID;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class LoginEntity extends IdentifierEntity {

    @Column(LOGIN)
    private String login;

    @Column(PASSWORD)
    private String password;

    @Column(USER_ID)
    private AggregateReference<UserEntity, Long> userId;

    public interface Names {
        String TABLE_NAME = "LOGINS";
        String SEQUENCE_NAME = "SEQ_PK_LOGIN_ID";

        String LOGIN = "LOGIN";
        String PASSWORD = "PASSWORD";
        String USER_ID = "USER_ID";
    }
}
