package com.lelar.instance;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TournamentInstance {
    private Long id;
    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String gender;
    private SquadInstance squadInstance;
    private Long squadCount;
    private SquadInstance opponentsSquadInstance;
    private Long opponentsSquadCount;
    private String address;
    private String status;
    private Set<PictureInstance> pictures;
}
