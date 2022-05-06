package depromeet.ohgzoo.iam.folder;

import depromeet.ohgzoo.iam.category.FirstCategory;
import depromeet.ohgzoo.iam.category.SecondCategory;
import depromeet.ohgzoo.iam.folder.folderItem.FolderItemCreateRequest;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
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
                FirstCategory.ANGRY,
                SecondCategory.UPSET,
                "content",
                List.of("tag1", "tag2", "tag3"),
                true,
                null,
                "post Id");

        folderEventSubscriber.handlePostCreateEvent(givenEvent);

        FolderItemCreateRequest expected = spyFolderService.createFolderItem_argumentRequest;

        assertThat(expected.getFirstCategory()).isEqualTo(FirstCategory.ANGRY);
        assertThat(expected.getSecondCategory()).isEqualTo(SecondCategory.UPSET);
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
}