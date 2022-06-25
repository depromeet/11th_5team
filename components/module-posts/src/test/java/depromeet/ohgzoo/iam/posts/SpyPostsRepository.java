package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SpyPostsRepository implements PostsRepository {
    public Posts save_entity;
    public Long findByMemberId_argumentId;
    public Pageable findByMemberId_argumentPageable;
    public Page<Posts> findByMemberId_returnValue = Page.empty();
    public boolean findAll_wasCalled;
    public List<Posts> findAll_returnValue = Collections.emptyList();
    public Optional<Posts> findById;
    public String findById_argumentId;
    public Posts findById_returnValue;
    public Long deletePostsByMemberId_argumentMemberId;

    @Override
    public List<Posts> findAll() {
        findAll_wasCalled = true;
        return findAll_returnValue;
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
    public List<Posts> findAllById(Iterable<String> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String aLong) {

    }

    @Override
    public void delete(Posts entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> longs) {

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
    public Optional<Posts> findById(String aLong) {
        findById_argumentId = aLong;
//        if (aLong.equals(0L)) {
//            this.findById = Optional.empty();
//        } else {
//            this.findById = Optional.of(Posts.builder().memberId(1L).build());
//        }

        return Optional.ofNullable(findById_returnValue);
    }

    @Override
    public boolean existsById(String aLong) {
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
    public void deleteAllByIdInBatch(Iterable<String> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Posts getOne(String aLong) {
        return null;
    }

    @Override
    public Posts getById(String aLong) {
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
    public Page<Posts> findByMemberId(Long memberId, Pageable pageable) {
        findByMemberId_argumentId = memberId;
        findByMemberId_argumentPageable = pageable;
        return findByMemberId_returnValue;
    }

    @Override
    public void bulkDeletePosts(List<String> postIds) {
    }

    @Override
    public void deletePostsByMemberId(Long memberId) {
        this.deletePostsByMemberId_argumentMemberId = memberId;
    }
}
