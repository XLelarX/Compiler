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

import static com.lelar.database.entity.ClassicTournamentEntity.Names.ADDRESS;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.END_DATE;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.GENDER;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.NAME;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.OPPONENTS_SQUAD_COUNT;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.OPPONENTS_SQUAD_NAME;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.SQUAD_COUNT;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.SQUAD_NAME;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.START_DATE;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.STATUS_ID;
import static com.lelar.database.entity.ClassicTournamentEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
@Sequence(SEQUENCE_NAME)
public class ClassicTournamentEntity extends IdentifierEntity {

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

    @Column(SQUAD_NAME)
    private String squadName;

    @Column(OPPONENTS_SQUAD_NAME)
    private String opponentsSquadName;

    @Column(STATUS_ID)
    private AggregateReference<TournamentStatusEntity, Long> statusId;

    @Column(ADDRESS)
    private String address;

    @MappedCollection(idColumn = "TOURNAMENT_ID", keyColumn = "PICTURE_ID")
    private Set<ClassicPictureBindingEntity> tournamentPictureRefs;

    public interface Names {
        String TABLE_NAME = "CLASSIC_TOURNAMENTS";

        String SEQUENCE_NAME = "SEQ_PK_CLASSIC_TOURNAMENTS_ID";

        String NAME = "TOURNAMENT_NAME";
        String START_DATE = "START_DATE";
        String END_DATE = "END_DATE";
        String GENDER = "GENDER";
        String SQUAD_COUNT = "SQUAD_COUNT";
        String OPPONENTS_SQUAD_COUNT = "OPPONENTS_SQUAD_COUNT";
        String SQUAD_NAME = "SQUAD_NAME";
        String OPPONENTS_SQUAD_NAME = "OPPONENTS_SQUAD_NAME";
        String STATUS_ID = "STATUS_ID";
        String ADDRESS = "ADDRESS";
    }
}