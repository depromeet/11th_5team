package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.member.MemberCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FolderEventSubscriber {
    private final FolderService folderService;

    @EventListener
    public void handlePostCreateEvent(PostCreateEvent event) {
        FolderItemCreateRequest request = new FolderItemCreateRequest(event.getPostId(), event.getFirstCategory(), event.getSecondCategory(), event.getContent(), event.getTags(), event.isDisclosure());
        folderService.createFolderItem(event.getMemberId(), event.getFolderId(), request);
    }

    @EventListener
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        folderService.deleteFolderItems(event.getMemberId(), event.getPostIds());
    }

    @EventListener
    public void handleMemberCreateEvent(MemberCreateEvent event) {
        folderService.createDefaultFolder(event.getMemberId());
    }
}