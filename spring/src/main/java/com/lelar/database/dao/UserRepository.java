package com.lelar.database.dao;

import com.lelar.database.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<UserEntity, Long> {

}
