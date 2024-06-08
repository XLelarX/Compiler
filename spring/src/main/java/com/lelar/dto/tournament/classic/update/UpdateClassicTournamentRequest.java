package com.lelar.dto.tournament.classic.update;

import com.lelar.dto.Gender;
import com.lelar.dto.Status;
import com.lelar.dto.picture.Picture;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UpdateClassicTournamentRequest {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Gender gender;
    private String address;
    private String squadName;
    private Long squadCount;
    private String opponentsSquadName;
    private Long opponentsSquadCount;
    private Set<Picture> pictures;
    private Status status;
}
