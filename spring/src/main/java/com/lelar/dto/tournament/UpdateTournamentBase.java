package com.lelar.dto.tournament;

import com.lelar.dto.Status;
import com.lelar.dto.picture.Picture;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateTournamentBase {
    private Long tournamentId;
    private Set<Picture> pictures;
    private Status status;
}
