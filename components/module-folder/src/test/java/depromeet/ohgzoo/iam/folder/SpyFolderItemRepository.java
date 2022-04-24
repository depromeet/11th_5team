package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SpyFolderItemRepository  implements FolderItemRepository {
    public FolderItem save_argumentFolderItem;

    @Override
    public List<FolderItem> findAll() {
        return null;
    }

    @Override
    public List<FolderItem> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<FolderItem> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<FolderItem> findAllById(Iterable<Long> longs) {
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
    public void delete(FolderItem entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends FolderItem> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends FolderItem> S save(S entity) {
        save_argumentFolderItem = entity;
        return null;
    }

    @Override
    public <S extends FolderItem> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<FolderItem> findById(Long aLong) {
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
    public <S extends FolderItem> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends FolderItem> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<FolderItem> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public FolderItem getOne(Long aLong) {
        return null;
    }

    @Override
    public FolderItem getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends FolderItem> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends FolderItem> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends FolderItem> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends FolderItem> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FolderItem> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FolderItem> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends FolderItem, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
