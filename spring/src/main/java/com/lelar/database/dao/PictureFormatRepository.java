package com.lelar.database.dao;

import com.lelar.database.entity.PictureFormatEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PictureFormatRepository extends ListCrudRepository<PictureFormatEntity, Long> {

    @Query("SELECT * FROM PICTURE_FORMATS WHERE FORMAT IN (:FORMATS)")
    List<PictureFormatEntity> findAllBy(@Param("FORMATS") Set<String> formats);
}
