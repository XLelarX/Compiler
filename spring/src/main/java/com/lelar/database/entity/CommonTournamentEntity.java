package com.lelar.database.entity;

import com.lelar.database.entity.id.IdentifierEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

import static com.lelar.database.entity.CommonTournamentEntity.Names.STATUS_ID;

@Data
@Accessors(chain = true)
public class CommonTournamentEntity<E extends CommonPictureBindingEntity> extends IdentifierEntity {

    @Column(STATUS_ID)
    private AggregateReference<TournamentStatusEntity, Long> statusId;

    @MappedCollection(idColumn = "TOURNAMENT_ID", keyColumn = "PICTURE_ID")
    private Set<E> tournamentPictureRefs;

    public interface Names {
        String STATUS_ID = "STATUS_ID";
    }
}