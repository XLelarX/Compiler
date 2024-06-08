package com.lelar.database.dao;

import com.lelar.database.entity.PictureEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface PictureRepository extends ListCrudRepository<PictureEntity, Long> {
}
