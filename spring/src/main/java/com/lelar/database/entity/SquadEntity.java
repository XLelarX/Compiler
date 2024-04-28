package com.lelar.database.entity;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.lelar.database.entity.SquadEntity.Names.NAME;
import static com.lelar.database.entity.SquadEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.SquadEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(name = TABLE_NAME, sequence = SEQUENCE_NAME)
public class SquadEntity extends IdentifierEntity {

    @Column(NAME)
    private String name;

    public interface Names {
        String TABLE_NAME = "squads";

        String SEQUENCE_NAME = "seq_pk_squad_id";

        String NAME = "name";
    }
}