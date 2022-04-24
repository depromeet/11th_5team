package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderItemRepository extends JpaRepository<FolderItem, Long> {

}
