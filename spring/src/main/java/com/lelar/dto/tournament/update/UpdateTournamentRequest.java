package com.lelar.dto.tournament.update;

import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.Tournament;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UpdateTournamentRequest {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String genderType;
    private String address;
    private Long squadId;
    private Long opponentSquadId;
    private Set<Picture> pictures;
}
