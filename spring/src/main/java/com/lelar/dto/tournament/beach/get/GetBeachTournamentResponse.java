package com.lelar.dto.tournament.beach.get;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class GetBeachTournamentResponse {
    private Set<Tournament> tournaments;
}
