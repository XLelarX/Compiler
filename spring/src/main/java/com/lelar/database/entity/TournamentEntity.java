package com.lelar.database.entity;

import com.lelar.database.annotation.Column;
import com.lelar.database.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

import static com.lelar.database.entity.TournamentEntity.Names.SEQUENCE_NAME;
import static com.lelar.database.entity.TournamentEntity.Names.TABLE_NAME;

@Data
@Accessors(chain = true)
@Table(name = TABLE_NAME, sequence = SEQUENCE_NAME)
public class TournamentEntity extends IdentifierEntity {
    @Column(Names.NAME)
    private String tournamentName;

    @Column(Names.START_DATE)
    private Timestamp startDate;

    @Column(Names.END_DATE)
    private Timestamp endDate;

    @Column(Names.GENDER_TYPE)
    private String genderType;

    @Column(Names.SQUAD_ID)
    private Long squadId;

    @Column(Names.OPPONENT_SQUAD_ID)
    private Long opponentSquadId;

    @Column(Names.ADDRESS)
    private String address;

    public interface Names {
        String TABLE_NAME = "tournaments";

        String SEQUENCE_NAME = "seq_pk_tournaments_id";

        String NAME = "tournament_name";
        String START_DATE = "start_date";
        String END_DATE = "end_date";
        String GENDER_TYPE = "gender_type";
        String SQUAD_ID = "squad_id";
        String OPPONENT_SQUAD_ID = "opponents_squad_id";
        String ADDRESS = "address";
    }
}