package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class TournamentEntity {
    private String tournamentName;
    private Timestamp startDate;
    private Timestamp endDate;
    private String genderType;
    private String squadName;
    private String opponentSquadName;
    private String address;

    public interface Names {
        String TABLE_NAME = "squads";

        String ID = "id";
        String NAME = "name";
    }
}