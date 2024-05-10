package com.lelar.database.generator;

import com.lelar.database.entity.LoginEntity;
import com.lelar.database.entity.PictureEntity;
import com.lelar.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.lelar.util.QueryHelper.getSequenceNextValue;

@Component
@RequiredArgsConstructor
public class PictureEntityIdGenerator implements BeforeConvertCallback<PictureEntity> {

    @Override
    public PictureEntity onBeforeConvert(PictureEntity aggregate) {
        Long nextPictureId = getSequenceNextValue(aggregate.getClass());
        aggregate.setId(nextPictureId);

        return aggregate;
    }

}
