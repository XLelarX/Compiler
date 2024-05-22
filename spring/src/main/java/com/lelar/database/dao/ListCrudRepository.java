package com.lelar.database.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ListCrudRepository<E, I> extends CrudRepository<E, I> {
    List<E> findAllById(Iterable<I> ids);

    <S extends E> List<S> saveAll(Iterable<S> entities);

    List<E> findAll();
}
