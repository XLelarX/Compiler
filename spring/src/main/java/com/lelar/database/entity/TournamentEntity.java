package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Set;

import static com.lelar.database.entity.TournamentEntity.Names.ADDRESS;
import static com.lelar.database.entity.TournamentEntity.Names.END_DATE;
import static com.lelar.database.entity.TournamentEntity.Names.GENDER_TYPE;
import static com.lelar.database.entity.TournamentEntity.Names.NAME;
import static com.lelar.database.entity.TournamentEntity.Names.OPPONENT_SQUAD_ID;
import static com.lelar.database.entity.TournamentEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.TournamentEntity.Names.SQUAD_ID;
import static com.lelar.database.entity.TournamentEntity.Names.START_DATE;
import static com.lelar.database.entity.TournamentEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class TournamentEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Column(NAME)
    private String tournamentName;

    @Column(START_DATE)
    private Timestamp startDate;

    @Column(END_DATE)
    private Timestamp endDate;

    @Column(GENDER_TYPE)
    private String genderType;

    @Column(SQUAD_ID)
    private AggregateReference<SquadEntity, Long> squadId;

    @Column(OPPONENT_SQUAD_ID)
    private AggregateReference<SquadEntity, Long> opponentSquadId;

    @Column(ADDRESS)
    private String address;

    @MappedCollection(idColumn = "TOURNAMENT_ID", keyColumn = "PICTURE_ID")
    private Set<PictureBindingEntity> tournamentPictureRefs;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public interface Names {
        String TABLE_NAME = "TOURNAMENTS";

        String SEQUENCE_NAME = "SEQ_PK_TOURNAMENTS_ID";

        String NAME = "TOURNAMENT_NAME";
        String START_DATE = "START_DATE";
        String END_DATE = "END_DATE";
        String GENDER_TYPE = "GENDER_TYPE";
        String SQUAD_ID = "SQUAD_ID";
        String OPPONENT_SQUAD_ID = "OPPONENTS_SQUAD_ID";
        String ADDRESS = "ADDRESS";
    }
}