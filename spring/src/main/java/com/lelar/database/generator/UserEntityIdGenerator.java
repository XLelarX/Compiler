package com.lelar.database.generator;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.UserEntity;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.lelar.util.QueryHelper.getSequenceNextValue;

@Component
public class UserEntityIdGenerator extends EntityIdGenerator<UserEntity> {

    @Override
    public UserEntity onBeforeConvert(UserEntity aggregate) {
        super.onBeforeConvert(aggregate);

        LoginEntity login = aggregate.getLogin();
        login.setId(getSequenceNextValue(login.getClass()));
        login.setUserId(AggregateReference.to(aggregate.getId()));

        Optional.ofNullable(aggregate.getPermissionUserRefs()).ifPresent(
            ref -> ref.forEach(
                it -> it.setUserId(AggregateReference.to(aggregate.getId()))
            )
        );
        Optional.ofNullable(aggregate.getSquadUserRefs()).ifPresent(
            ref -> ref.forEach(
                it -> it.setUserId(AggregateReference.to(aggregate.getId()))
            )
        );

        return aggregate;
    }

}
