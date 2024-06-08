package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.TournamentStatusEntity.Names.DESCRIPTION;
import static com.lelar.database.entity.TournamentStatusEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.TournamentStatusEntity.Names.STATUS_KEY;
import static com.lelar.database.entity.TournamentStatusEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class TournamentStatusEntity extends IdentifierEntity {

    @Column(STATUS_KEY)
    private String statusKey;

    @Column(DESCRIPTION)
    private String description;

    public interface Names {
        String TABLE_NAME = "TOURNAMENT_STATUS";
        String SEQUENCE_NAME = "SEQ_PK_LOGIN_ID";

        String STATUS_KEY = "STATUS_KEY";
        String DESCRIPTION = "DESCRIPTION";
    }

}
