package depromeet.ohgzoo.iam.folder.folderItem;

import depromeet.ohgzoo.iam.folder.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderItemRepository extends JpaRepository<FolderItem, Long> {
    FolderItem findByFolderId(Folder folder);
    FolderItem findFirstByFolderOrderByCreatedAtDesc(Folder folder);
}
