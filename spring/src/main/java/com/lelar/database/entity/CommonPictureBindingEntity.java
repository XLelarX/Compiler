package com.lelar.database.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import static com.lelar.database.entity.CommonPictureBindingEntity.Names.PICTURE_ID;
import static com.lelar.database.entity.CommonPictureBindingEntity.Names.TOURNAMENT_ID;

@Data
@Accessors(chain = true)
public class CommonPictureBindingEntity<E extends CommonTournamentEntity> {

    @Column(TOURNAMENT_ID)
    private AggregateReference<E, Long> tournamentId;

    @Column(PICTURE_ID)
    private AggregateReference<PictureEntity, Long> pictureId;

    public interface Names {
        String TABLE_NAME = "CLASSIC_PICTURE_BINDINGS";

        String TOURNAMENT_ID = "TOURNAMENT_ID";
        String PICTURE_ID = "PICTURE_ID";
    }
}