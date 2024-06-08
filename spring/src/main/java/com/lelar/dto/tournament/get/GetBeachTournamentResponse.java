package com.lelar.dto.tournament.get;

import com.lelar.dto.tournament.Tournament;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class GetBeachTournamentResponse {
    private Set<Tournament> tournaments;
}
