package depromeet.ohgzoo.iam.search.batch;

import depromeet.ohgzoo.iam.folderEvent.FolderItemDeleteEvent;
import depromeet.ohgzoo.iam.member.MemberDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.EncryptEvent;
import depromeet.ohgzoo.iam.postEvent.IncreaseViewEvent;
import depromeet.ohgzoo.iam.postEvent.PostCreateEvent;
import depromeet.ohgzoo.iam.postEvent.PostDeleteEvent;
import depromeet.ohgzoo.iam.postEvent.PostUpdateEvent;
import depromeet.ohgzoo.iam.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class SearchEventSubscriber {
    private final SearchRepository searchRepository;


    @EventListener
    public void handleEncryptEvent(EncryptEvent event) {
        List<SearchEntity> all = searchRepository.findAll();

        all.forEach(item -> {
            try {
                item.updateContent(EncryptUtil.encrypt(item.getContent()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventListener
    public void handlePostCreateEvent(PostCreateEvent event) {
        searchRepository.save(new SearchEntity(
                event.getPostId(),
                event.getMemberId(),
                event.getFirstCategory(),
                event.getSecondCategory(),
                event.getContent(),
                event.getTags(),
                0));
    }

    @EventListener
    public void handlePostUpdateEvent(PostUpdateEvent event) {
        SearchEntity searchEntity = searchRepository.findById(event.getPostId())
                .get();

        searchEntity.update(new UpdatePostRequest(
                event.getMemberId(),
                event.getSecondCategory(),
                event.getContent(),
                event.getTags()));
    }

    @EventListener
    public void handlePostDeleteEvent(PostDeleteEvent event) {
        searchRepository.deleteAllById(event.getPostIds());
    }

    @EventListener
    public void handleFolderDeleteEvent(FolderItemDeleteEvent event) {
        searchRepository.deleteAllById(event.getPostIds());
    }

    @EventListener
    public void handleIncreaseViewEvent(IncreaseViewEvent event) {
        SearchEntity searchEntity = searchRepository.findById(event.getPostId())
                .get();
        searchEntity.increaseViews();
    }

    @EventListener
    public void handleMemberDeleteEvent(MemberDeleteEvent memberDeleteEvent) {
        searchRepository.deleteByMemberId(memberDeleteEvent.getMemberId());
    }
}
