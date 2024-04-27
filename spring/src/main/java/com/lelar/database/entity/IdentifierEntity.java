package com.lelar.database.entity;

import com.lelar.database.annotation.Column;
import lombok.Data;

@Data
public class IdentifierEntity {
    @Column(Names.ID)
    private Long id;

    public interface Names {
        String ID = "id";
    }
}
