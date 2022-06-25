package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemUpdateRequest;
import depromeet.ohgzoo.iam.member.MemberCreateEvent;
import depromeet.ohgzoo.iam.member.MemberDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.IncreaseViewEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.PostUpdateEvent;
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
    public void handlePostUpdateEvent(PostUpdateEvent event) {
        FolderItemUpdateRequest request = new FolderItemUpdateRequest(event.getSecondCategory(), event.getContent(), event.getTags(), event.isDisclosure(), event.getFolderId());
        folderService.updateFolderItem(event.getMemberId(), event.getPostId(), request);
    }

    @EventListener
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        folderService.deleteFolderItems(event.getMemberId(), event.getPostIds());
    }

    @EventListener
    public void handleMemberCreateEvent(MemberCreateEvent event) {
        folderService.createDefaultFolder(event.getMemberId());
    }

    @EventListener
    public void handleIncreaseViewEvent(IncreaseViewEvent event) {
        folderService.increaseViews(event.getPostId());
    }

    @EventListener
    public void handleMemberDeleteEvent(MemberDeleteEvent memberDeleteEvent) {
        folderService.deleteAllFolders(memberDeleteEvent.getMemberId());
    }
}