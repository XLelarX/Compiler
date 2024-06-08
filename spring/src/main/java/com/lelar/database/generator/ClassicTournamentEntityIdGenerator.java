package com.lelar.database.generator;

import com.lelar.database.entity.ClassicTournamentEntity;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

@Component
public class ClassicTournamentEntityIdGenerator extends EntityIdGenerator<ClassicTournamentEntity> {

    @Override
    public ClassicTournamentEntity onBeforeConvert(ClassicTournamentEntity aggregate) {
        super.onBeforeConvert(aggregate);
        aggregate.getTournamentPictureRefs().forEach(
            it -> it.setTournamentId(AggregateReference.to(aggregate.getId()))
        );

        return aggregate;
    }

}
