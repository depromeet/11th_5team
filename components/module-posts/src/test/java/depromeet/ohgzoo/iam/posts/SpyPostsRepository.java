package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SpyPostsRepository implements PostsRepository {
    public Posts save_entity;
    public Long findByMemberId_argumentId;
    public Posts findByMemberId_returnValue = Posts.builder().build();
    public String findByTag_argumentTag;

    @Override
    public List<Posts> findAll() {
        return null;
    }

    @Override
    public List<Posts> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Posts> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Posts> findAllById(Iterable<Long> longs) {
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
    public void delete(Posts entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Posts> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Posts> S save(S entity) {
        save_entity = entity;
        return null;
    }

    @Override
    public <S extends Posts> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Posts> findById(Long aLong) {
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
    public <S extends Posts> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Posts> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Posts> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Posts getOne(Long aLong) {
        return null;
    }

    @Override
    public Posts getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Posts> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Posts> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Posts> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Posts> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Posts> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Posts> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Posts, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public Page<Posts> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable) {
        findByMemberId_argumentId = memberId;
        return null;
    }

    @Override
    public Page<Posts> findByTagsOrderByIdDesc(String tag, Pageable pageable) {
        findByTag_argumentTag = tag;
        return null;
    }

    @Override
    public Page<Posts> findAllOrderByViewDesc(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Posts> findTop1ByMemberIdAndSecondCategoryAndCreatedAtGreaterThanEqualOrderByIdDesc(Long memberId, PostsSecondCategory secondCategory, LocalDateTime weekAgo) {
        findByMemberId_argumentId = memberId;
        return null;
    }
}
