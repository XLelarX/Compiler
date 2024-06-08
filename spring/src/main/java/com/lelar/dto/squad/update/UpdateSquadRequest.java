package com.lelar.dto.squad.update;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateSquadRequest {
    private Long squadId;
    private String name;
    private Set<Long> usersIds;
}
