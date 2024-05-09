package com.lelar.database.generator;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.lelar.util.QueryHelper.getSequenceNextValue;

@Component
@RequiredArgsConstructor
public class UserEntityIdGenerator implements BeforeConvertCallback<UserEntity> {

    @Override
    public UserEntity onBeforeConvert(UserEntity aggregate) {
        Long nextUserId = getSequenceNextValue(aggregate.getClass());
        aggregate.setId(nextUserId);

        LoginEntity login = aggregate.getLogin();
        login.setId(getSequenceNextValue(login.getClass()));
        login.setUserId(AggregateReference.to(nextUserId));

        Optional.ofNullable(aggregate.getPermissionUserRefs()).ifPresent(
            ref -> ref.forEach(
                it -> it.setUserId(AggregateReference.to(nextUserId))
            )
        );
        Optional.ofNullable(aggregate.getSquadUserRefs()).ifPresent(
            ref -> ref.forEach(
                it -> it.setUserId(AggregateReference.to(nextUserId))
            )
        );

        return aggregate;
    }

}
