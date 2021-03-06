package depromeet.ohgzoo.iam.folder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByMemberIdAndName(Long memberId, String name);

    Folder findByMemberIdAndIsDefaultTrue(Long memberId);

    List<Folder> findAllByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
