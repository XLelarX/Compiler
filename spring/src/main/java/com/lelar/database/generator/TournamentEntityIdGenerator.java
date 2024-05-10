package com.lelar.database.generator;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.TournamentEntity;
import com.lelar.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.lelar.util.QueryHelper.getSequenceNextValue;

@Component
@RequiredArgsConstructor
public class TournamentEntityIdGenerator implements BeforeConvertCallback<TournamentEntity> {

    @Override
    public TournamentEntity onBeforeConvert(TournamentEntity aggregate) {
        Long nextTournamentId = getSequenceNextValue(aggregate.getClass());
        aggregate.setId(nextTournamentId);
        aggregate.getTournamentPictureRefs().forEach(
            it -> it.setTournamentId(AggregateReference.to(nextTournamentId))
        );

        return aggregate;
    }

}
