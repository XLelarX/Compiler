package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Set;

import static com.lelar.database.entity.UserEntity.Names.BIRTH_DATE;
import static com.lelar.database.entity.UserEntity.Names.EMAIL;
import static com.lelar.database.entity.UserEntity.Names.FIRST_NAME;
import static com.lelar.database.entity.UserEntity.Names.GENDER;
import static com.lelar.database.entity.UserEntity.Names.PATRONYMIC;
import static com.lelar.database.entity.UserEntity.Names.RATING;
import static com.lelar.database.entity.UserEntity.Names.SECOND_NAME;
import static com.lelar.database.entity.UserEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.UserEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class UserEntity extends IdentifierEntity {

    @Column(FIRST_NAME)
    private String firstName;

    @Column(SECOND_NAME)
    private String secondName;

    @Column(PATRONYMIC)
    private String patronymic;

    @Column(EMAIL)
    private String email;

    @Column(BIRTH_DATE)
    private Timestamp birthDate;

    @MappedCollection(idColumn = "USER_ID", keyColumn = "PERMISSION_ID")
    private Set<PermissionBindingEntity> permissionUserRefs;

    @MappedCollection(idColumn = "USER_ID", keyColumn = "SQUAD_ID")
    private Set<SquadBindingEntity> squadUserRefs;

    @MappedCollection(idColumn = "USER_ID")
    private LoginEntity login;

    @Column(RATING)
    private Long rating;

    @Column(GENDER)
    private String gender;

    public interface Names {
        String TABLE_NAME = "USERS";
        String SEQUENCE_NAME = "SEQ_PK_USER_ID";

        String FIRST_NAME = "FIRST_NAME";
        String SECOND_NAME = "SECOND_NAME";
        String PATRONYMIC = "PATRONYMIC";
        String EMAIL = "EMAIL";
        String BIRTH_DATE = "BIRTH_DATE";
        String RATING = "RATING";
        String GENDER = "GENDER";
    }


}