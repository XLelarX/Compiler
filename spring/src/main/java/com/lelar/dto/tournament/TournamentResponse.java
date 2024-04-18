package com.lelar.dto.tournament;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TournamentResponse {
    private List<Tournament> tournaments;
}
