package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FolderEventSubscriberTest {
    private FolderEventSubscriber folderEventSubscriber;
    private SpyFolderService spyFolderService;

    @BeforeEach
    void setUp() {
        spyFolderService = new SpyFolderService();
        folderEventSubscriber = new FolderEventSubscriber(spyFolderService);
    }

    @Test
    void handlePostCreateEvent_passesRequestToService() {
        PostCreateEvent givenEvent = new PostCreateEvent(this,
                null,
                FirstCategory.SADNESS,
                SecondCategory.SADNESS,
                "content",
                List.of("tag1", "tag2", "tag3"),
                true,
                null,
                "post Id");

        folderEventSubscriber.handlePostCreateEvent(givenEvent);

        FolderItemCreateRequest expected = spyFolderService.createFolderItem_argumentRequest;

        assertThat(expected.getFirstCategory()).isEqualTo(FirstCategory.SADNESS);
        assertThat(expected.getSecondCategory()).isEqualTo(SecondCategory.SADNESS);
        assertThat(expected.getPostId()).isEqualTo("post Id");
        assertThat(expected.getContent()).isEqualTo("content");
        assertThat(expected.getTags()).containsExactly("tag1", "tag2", "tag3");
        assertThat(expected.getDisclosure()).isTrue();
    }

    @Test
    void handlePostCreateEvent_passesMemberIdAndFolderIdToService() {
        PostCreateEvent givenEvent = new PostCreateEvent(this,
                1L,
                null,
                null,
                null,
                Collections.emptyList(),
                false,
                1L,
                null);

        folderEventSubscriber.handlePostCreateEvent(givenEvent);

        assertThat(spyFolderService.createFolderItem_argumentMemberId).isEqualTo(1L);
        assertThat(spyFolderService.createFolderItem_argumentFolderId).isEqualTo(1L);
    }

    @Test
    void handlePostDeleteEvent_passesMemberIdToService() {
        PostDeleteEvent givenEvent = new PostDeleteEvent(this,
                1L,
                List.of());
        folderEventSubscriber.handlePostDeleteEvent(givenEvent);
        assertThat(spyFolderService.argument_memberId).isEqualTo(1L);
    }

    @Test
    void handlePostDeleteEvent_passesPostIdsToService() {
        PostDeleteEvent givenEvent = new PostDeleteEvent(this,
                null,
                List.of("1", "2", "3"));
        folderEventSubscriber.handlePostDeleteEvent(givenEvent);
        assertThat(spyFolderService.deleteFolderItems_argumentPostIds).isEqualTo(List.of("1", "2", "3"));
    }
}