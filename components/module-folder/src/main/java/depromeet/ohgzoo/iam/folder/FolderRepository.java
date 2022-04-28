package depromeet.ohgzoo.iam.folder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByName(String name);

    List<Folder> findAllByMemberId(Long memberId);
}
