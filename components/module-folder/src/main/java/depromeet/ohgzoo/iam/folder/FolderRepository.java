package depromeet.ohgzoo.iam.folder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Optional<Folder> findByName(String name);
//    FolderItem findFirstByOrderByIdDesc();

//    FolderItem findFirstByFolderItemsBy
//select id from table order by id desc limit 1;

//    SELECT * FROM "TABLE NAME" ORDER BY "COLUMN NAME" DESC LIMIT 1
}
