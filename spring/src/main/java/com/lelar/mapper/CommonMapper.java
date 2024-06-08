package com.lelar.mapper;

import com.lelar.dto.Gender;
import com.lelar.dto.Status;
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

    default Gender mapGender(String gender) {
        return Gender.getGenderByKey(gender);
    }

    default String mapGender(Gender gender) {
        return gender.getGenderKey();
    }

    default String mapStatus(Status status) {
        return status.name();
    }

    default Status mapStatus(String status) {
        return Status.valueOf(status);
    }

}
