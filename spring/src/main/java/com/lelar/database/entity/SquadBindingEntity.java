package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.SquadBindingEntity.Names.SQUAD_ID;
import static com.lelar.database.entity.SquadBindingEntity.Names.TABLE_NAME;
import static com.lelar.database.entity.SquadBindingEntity.Names.USER_ID;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class SquadBindingEntity {

    @Column(SQUAD_ID)
    private AggregateReference<SquadEntity, Long> squadId;

    @Column(USER_ID)
    private AggregateReference<UserEntity, Long> userId;

    public interface Names {
        String TABLE_NAME = "SQUAD_BINDINGS";

        String SQUAD_ID = "SQUAD_ID";
        String USER_ID = "USER_ID";
    }
}
