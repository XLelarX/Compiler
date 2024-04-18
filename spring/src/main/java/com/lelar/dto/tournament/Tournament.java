package com.lelar.dto.tournament;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tournament {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String genderType;
    private String squadName;
    private String opponentSquadName;
    private String address;
}
