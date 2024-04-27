package com.lelar.dto.tournament.get;

import com.lelar.dto.tournament.Tournament;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetTournamentResponse {
    private List<Tournament> tournaments;
}
