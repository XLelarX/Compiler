package com.lelar.dto.tournament.update;

import com.lelar.dto.Gender;
import com.lelar.dto.Status;
import com.lelar.dto.picture.Picture;
import com.lelar.dto.tournament.Tournament;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UpdateBeachTournamentRequest {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Gender gender;
    private String address;
    private Long squadId;
    private Long squadCount;
    private Long opponentsSquadId;
    private Long opponentsSquadCount;
    private Set<Picture> pictures;

    //TODO допилить
    private Status status;
}
