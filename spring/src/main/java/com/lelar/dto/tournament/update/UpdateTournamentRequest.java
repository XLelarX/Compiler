package com.lelar.dto.tournament.update;

import com.lelar.database.entity.TournamentEntity;
import lombok.Data;

@Data
public class UpdateTournamentRequest {
    private TournamentEntity entity;

}
