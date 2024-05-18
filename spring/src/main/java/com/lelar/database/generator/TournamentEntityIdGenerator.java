package com.lelar.database.generator;

import com.lelar.database.entity.TournamentEntity;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

@Component
public class TournamentEntityIdGenerator extends EntityIdGenerator<TournamentEntity> {

    @Override
    public TournamentEntity onBeforeConvert(TournamentEntity aggregate) {
        super.onBeforeConvert(aggregate);
        aggregate.getTournamentPictureRefs().forEach(
            it -> it.setTournamentId(AggregateReference.to(aggregate.getId()))
        );

        return aggregate;
    }

}
