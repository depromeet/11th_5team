package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query(value = "select p from Posts p where p.memberId = ?1 order by p.id desc",
            countQuery = "select count(p.memberId) from Posts p")
    Page<Posts> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);
    @Query(value = "select p from Posts p where p.disclosure = true and find_in_set(?1, p.tags) > 0 order by p.id desc",
            countQuery = "select count(*) from Posts p where find_in_set(?1, p.tags) > 0")
    Page<Posts> findByTagsOrderByIdDesc(String tag, Pageable pageable);
    @Query(value = "select p from Posts p where p.disclosure = true order by p.views desc",
            countQuery = "select count(*) from Posts p")
    Page<Posts> findAllOrderByViewDesc(Pageable pageable);
//    @Query("SELECT p FROM Posts p WHERE p.memberId = ?1 AND p.secondCategory = ?2 AND p.createdAt >= ?3  order by p.id desc")
    Optional<Posts> findTop1ByMemberIdAndSecondCategoryAndCreatedAtGreaterThanEqualOrderByIdDesc(Long memberId, PostsSecondCategory secondCategory, LocalDateTime weekAgo);
}
