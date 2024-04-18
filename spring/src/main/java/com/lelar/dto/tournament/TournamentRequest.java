package com.lelar.dto.tournament;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TournamentRequest {
    private LocalDateTime startBeginDate;
    private LocalDateTime startEndDate;
}
