package com.lelar.dto.tournament.get;

import com.lelar.dto.Gender;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetTournamentRequest {
    private LocalDateTime startBeginDate;
    private LocalDateTime startEndDate;
    private Gender gender;
}
