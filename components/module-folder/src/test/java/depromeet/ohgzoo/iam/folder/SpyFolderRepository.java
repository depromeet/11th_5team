package depromeet.ohgzoo.iam.folder;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SpyFolderRepository implements FolderRepository {
    public Folder save_argumentFolder;
    public Folder save_returnValue = Folder.builder().build();

    @Override
    public List<Folder> findAll() {
        return null;
    }

    @Override
    public List<Folder> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Folder> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Folder> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Folder entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Folder> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Folder> S save(S entity) {
        save_argumentFolder = entity;
        return null;
    }

    @Override
    public <S extends Folder> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Folder> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Folder> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Folder> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Folder> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Folder getOne(Long aLong) {
        return null;
    }

    @Override
    public Folder getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Folder> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Folder> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Folder> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Folder> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Folder> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Folder> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Folder, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}


