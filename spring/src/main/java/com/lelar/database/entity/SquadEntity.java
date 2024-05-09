package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.SquadEntity.Names.NAME;
import static com.lelar.database.entity.SquadEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.SquadEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class SquadEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Column(NAME)
    private String name;

    @Override
    public boolean isNew() {
        return true;
    }

    public interface Names {
        String TABLE_NAME = "SQUADS";

        String SEQUENCE_NAME = "SEQ_PK_SQUAD_ID";

        String NAME = "NAME";
    }
}