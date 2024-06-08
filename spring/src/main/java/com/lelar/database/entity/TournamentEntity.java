package com.lelar.database.entity;

import com.lelar.database.annotation.Sequence;
import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Set;

import static com.lelar.database.entity.TournamentEntity.Names.ADDRESS;
import static com.lelar.database.entity.TournamentEntity.Names.END_DATE;
import static com.lelar.database.entity.TournamentEntity.Names.GENDER;
import static com.lelar.database.entity.TournamentEntity.Names.NAME;
import static com.lelar.database.entity.TournamentEntity.Names.OPPONENTS_SQUAD_COUNT;
import static com.lelar.database.entity.TournamentEntity.Names.OPPONENTS_SQUAD_ID;
import static com.lelar.database.entity.TournamentEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.TournamentEntity.Names.SQUAD_COUNT;
import static com.lelar.database.entity.TournamentEntity.Names.SQUAD_ID;
import static com.lelar.database.entity.TournamentEntity.Names.START_DATE;
import static com.lelar.database.entity.TournamentEntity.Names.STATUS_ID;
import static com.lelar.database.entity.TournamentEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class TournamentEntity extends IdentifierEntity {

    @Column(NAME)
    private String tournamentName;

    @Column(START_DATE)
    private Timestamp startDate;

    @Column(END_DATE)
    private Timestamp endDate;

    @Column(GENDER)
    private String gender;

    @Column(SQUAD_COUNT)
    private Long squadCount;

    @Column(OPPONENTS_SQUAD_COUNT)
    private Long opponentsSquadCount;

    @Column(SQUAD_ID)
    private AggregateReference<SquadEntity, Long> squadId;

    @Column(OPPONENTS_SQUAD_ID)
    private AggregateReference<SquadEntity, Long> opponentsSquadId;

    @Column(STATUS_ID)
    private AggregateReference<TournamentStatusEntity, Long> statusId;

    @Column(ADDRESS)
    private String address;

    @MappedCollection(idColumn = "TOURNAMENT_ID", keyColumn = "PICTURE_ID")
    private Set<PictureBindingEntity> tournamentPictureRefs;

    public interface Names {
        String TABLE_NAME = "TOURNAMENTS";

        String SEQUENCE_NAME = "SEQ_PK_TOURNAMENTS_ID";

        String NAME = "TOURNAMENT_NAME";
        String START_DATE = "START_DATE";
        String END_DATE = "END_DATE";
        String GENDER = "GENDER";
        String SQUAD_COUNT = "SQUAD_COUNT";
        String OPPONENTS_SQUAD_COUNT = "OPPONENTS_SQUAD_COUNT";
        String SQUAD_ID = "SQUAD_ID";
        String OPPONENTS_SQUAD_ID = "OPPONENTS_SQUAD_ID";
        String ADDRESS = "ADDRESS";
        String STATUS_ID = "STATUS_ID";
    }
}