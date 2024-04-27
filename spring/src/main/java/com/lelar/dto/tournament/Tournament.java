package com.lelar.dto.tournament;

import com.lelar.dto.picture.PictureData;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Tournament {
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String genderType;
    private String squadName;
    private String opponentSquadName;
    private String address;
    private List<PictureData> pictures;
}
