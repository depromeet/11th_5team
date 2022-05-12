package depromeet.ohgzoo.iam.search;

import depromeet.ohgzoo.iam.search.batch.SearchEntity;
import depromeet.ohgzoo.iam.search.batch.SearchRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SpySearchRepository implements SearchRepository {
    public boolean findAll_wasCalled;
    public List<SearchEntity> findAll_returnValue = Collections.emptyList();
    public Iterable<SearchEntity> saveAll_argumentList;

    @Override
    public List<SearchEntity> findAll() {
        findAll_wasCalled = true;
        return findAll_returnValue;
    }

    @Override
    public List<SearchEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SearchEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<SearchEntity> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(SearchEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends SearchEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends SearchEntity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SearchEntity> List<S> saveAll(Iterable<S> entities) {
        saveAll_argumentList = (Iterable<SearchEntity>) entities;
        return null;
    }

    @Override
    public Optional<SearchEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends SearchEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SearchEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<SearchEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SearchEntity getOne(String s) {
        return null;
    }

    @Override
    public SearchEntity getById(String s) {
        return null;
    }

    @Override
    public <S extends SearchEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SearchEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends SearchEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends SearchEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SearchEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SearchEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SearchEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
