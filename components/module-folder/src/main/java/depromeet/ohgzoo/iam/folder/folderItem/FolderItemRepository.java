package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderItemRepository extends JpaRepository<FolderItem, Long> {
    Optional<FolderItem> findByPostId(Long postId);
    FolderItem findFirstByFolderOrderByCreatedAtDesc(Folder folder);
}
