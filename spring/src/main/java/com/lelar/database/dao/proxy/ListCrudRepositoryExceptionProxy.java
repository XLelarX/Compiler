package com.lelar.database.dao.proxy;

import com.lelar.exception.DbException;
import com.lelar.util.VoidSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
public class ListCrudRepositoryExceptionProxy<T, I> implements ListCrudRepository<T, I> {

    private final ListCrudRepository<T, I> repository;
    private final String tableName;

    @Override
    public <S extends T> S save(S entity) {
        return processException(() -> repository.save(entity));
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return processException(() -> repository.saveAll(entities));
    }

    @Override
    public Optional<T> findById(I i) {
        return processException(() -> repository.findById(i));
    }

    @Override
    public boolean existsById(I i) {
        return processException(() -> repository.existsById(i));
    }

    @Override
    public List<T> findAll() {
        return processException(repository::findAll);
    }

    @Override
    public List<T> findAllById(Iterable<I> is) {
        return processException(() -> repository.findAllById(is));
    }

    @Override
    public long count() {
        return processException(repository::count);
    }

    @Override
    public void deleteById(I i) {
        processException(() -> repository.deleteById(i));
    }

    @Override
    public void delete(T entity) {
        processException(() -> repository.delete(entity));
    }

    @Override
    public void deleteAllById(Iterable<? extends I> is) {
        processException(() -> repository.deleteAllById(is));
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        processException(() -> repository.deleteAll(entities));
    }

    @Override
    public void deleteAll() {
        processException((VoidSupplier) repository::deleteAll);
    }

    private <R> R processException(Supplier<R> action) {
        try {
            return action.get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw DbException.of(tableName);
        }
    }

    private void processException(VoidSupplier action) {
        try {
            action.get();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw DbException.of(tableName);
        }
    }


}
