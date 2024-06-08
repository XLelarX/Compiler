package com.lelar.dto.tournament.beach.get;

import com.lelar.dto.Gender;
import com.lelar.dto.Status;
import com.lelar.dto.picture.Picture;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class GetTournamentDetailResponse {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Gender gender;
    private String address;
    private SquadDetail squadDetail;
    private Long squadCount;
    private SquadDetail opponentSquadDetail;
    private Long opponentsSquadCount;
    private Set<Picture> pictures;
    private Status status;
}
