package depromeet.ohgzoo.iam.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, String> {
    List<Posts> findByMemberId(Long memberId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Posts p where p.id in :postIds")
    void bulkDeletePosts(@Param("postIds") List<String> postIds);
}