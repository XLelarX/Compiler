package com.lelar.database.generator;

import com.lelar.database.entity.SquadEntity;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

@Component
public class SquadEntityIdGenerator extends EntityIdGenerator<SquadEntity> {

    @Override
    public SquadEntity onBeforeConvert(SquadEntity aggregate) {
        super.onBeforeConvert(aggregate);
        aggregate.getSquadUserRefs().forEach(
            it -> it.setSquadId(AggregateReference.to(aggregate.getId()))
        );

        return aggregate;
    }

}
