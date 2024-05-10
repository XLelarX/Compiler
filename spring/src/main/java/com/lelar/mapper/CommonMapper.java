package com.lelar.mapper;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface CommonMapper {

    default <T> AggregateReference<T, Long> mapId(Long id) {
        return AggregateReference.to(id);
    }

    default <T> Long mapId(AggregateReference<T, Long> id) {
        return id.getId();
    }

    default Timestamp mapLocalDateTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

}
