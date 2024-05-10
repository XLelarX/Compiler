package com.lelar.instance;

import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.PictureFormatEntity;
import com.lelar.database.entity.TournamentEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class TournamentInstance {
    private Long id;
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String genderType;
    private Long squadId;
    private Long opponentSquadId;
    private String address;
    private Set<PictureInstance> pictures;
}
