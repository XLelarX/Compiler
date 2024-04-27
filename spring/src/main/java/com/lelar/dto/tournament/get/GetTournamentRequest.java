package com.lelar.dto.tournament.get;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetTournamentRequest {
    private LocalDateTime startBeginDate;
    private LocalDateTime startEndDate;
    private boolean needToLoadPictures;
}
