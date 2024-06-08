package com.lelar.dto.tournament.beach.update;

import com.lelar.dto.Gender;
import com.lelar.dto.tournament.UpdateTournamentBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UpdateBeachTournamentRequest extends UpdateTournamentBase {
    private Long squadId;
    private Long opponentsSquadId;

    private String tournamentName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Gender gender;
    private String address;
    private Long squadCount;
    private Long opponentsSquadCount;
}
