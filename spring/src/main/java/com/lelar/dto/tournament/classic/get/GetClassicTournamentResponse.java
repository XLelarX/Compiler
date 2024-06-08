package com.lelar.dto.tournament.classic.get;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class GetClassicTournamentResponse {
    private Set<ClassicTournament> tournaments;
}
