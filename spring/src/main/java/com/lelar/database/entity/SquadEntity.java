package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SquadEntity {
    private Long id;
    private String name;

    public interface Names {
        String TABLE_NAME = "squads";

        String ID = "id";
        String NAME = "name";
    }
}