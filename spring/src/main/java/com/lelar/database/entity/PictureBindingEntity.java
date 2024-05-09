package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.lelar.database.entity.PictureBindingEntity.Names.PICTURE_ID;
import static com.lelar.database.entity.PictureBindingEntity.Names.TABLE_NAME;
import static com.lelar.database.entity.PictureBindingEntity.Names.TOURNAMENT_ID;

@Data
@Accessors(chain = true)
@Table(TABLE_NAME)
public class PictureBindingEntity {

    @Column(TOURNAMENT_ID)
    private AggregateReference<TournamentEntity, Long> tournamentId;

    @Column(PICTURE_ID)
    private AggregateReference<PictureEntity, Long> pictureId;

    public interface Names {
        String TABLE_NAME = "PICTURE_BINDINGS";

        String TOURNAMENT_ID = "TOURNAMENT_ID";
        String PICTURE_ID = "PICTURE_ID";
    }
}