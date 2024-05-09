package com.lelar.dto.tournament;

import com.lelar.dto.picture.Picture;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Tournament {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String genderType;
    private String squadName;
    private String opponentSquadName;
    private String address;
    private Set<Picture> pictures;
}
