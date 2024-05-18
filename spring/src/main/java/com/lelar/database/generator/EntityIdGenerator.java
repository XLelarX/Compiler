package com.lelar.database.generator;

import com.lelar.database.entity.id.IdentifierEntity;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import static com.lelar.util.QueryHelper.getSequenceNextValue;

public class EntityIdGenerator<T extends IdentifierEntity> implements BeforeConvertCallback<T> {

    @Override
    public T onBeforeConvert(T aggregate) {
        if (!aggregate.isNew()) {
            return aggregate;
        }

        Long nextPictureId = getSequenceNextValue(aggregate.getClass());
        aggregate.setId(nextPictureId);

        return aggregate;
    }

}
