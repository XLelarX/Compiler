package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Set;

import static com.lelar.database.entity.UserEntity.Names.BIRTH_DATE;
import static com.lelar.database.entity.UserEntity.Names.FIRST_NAME;
import static com.lelar.database.entity.UserEntity.Names.PATRONYMIC;
import static com.lelar.database.entity.UserEntity.Names.SECOND_NAME;
import static com.lelar.database.entity.UserEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.UserEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class UserEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Column(FIRST_NAME)
    private String firstName;

    @Column(SECOND_NAME)
    private String secondName;

    @Column(PATRONYMIC)
    private String patronymic;

    @Column(BIRTH_DATE)
    private Timestamp birthDate;

    @MappedCollection(idColumn = "USER_ID", keyColumn = "PERMISSION_ID")
    private Set<PermissionBindingEntity> permissionUserRefs;

    @MappedCollection(idColumn = "USER_ID", keyColumn = "SQUAD_ID")
    private Set<SquadBindingEntity> squadUserRefs;

    @MappedCollection(idColumn = "USER_ID")
    private LoginEntity login;

    @Override
    public boolean isNew() {
        return true;
    }

    public interface Names {
        String TABLE_NAME = "USERS";
        String SEQUENCE_NAME = "SEQ_PK_USER_ID";

        String FIRST_NAME = "FIRST_NAME";
        String SECOND_NAME = "SECOND_NAME";
        String PATRONYMIC = "PATRONYMIC";
        String BIRTH_DATE = "BIRTH_DATE";
    }



}