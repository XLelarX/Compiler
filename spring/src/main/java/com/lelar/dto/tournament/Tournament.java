package com.lelar.dto.tournament;

import com.lelar.dto.Gender;
import com.lelar.dto.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tournament {
    private Long id;
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Gender gender;
    private String squadName;
    private Long squadCount;
    private String opponentsSquadName;
    private Long opponentsSquadCount;
    private String address;
    private Status status;
}
