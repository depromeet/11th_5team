package depromeet.ohgzoo.iam.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, String> {
    Page<Posts> findByMemberId(Long memberId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("delete from Posts p where p.id in :postIds")
    void bulkDeletePosts(@Param("postIds") List<String> postIds);

    void deletePostsByMemberId(Long memberId);
}