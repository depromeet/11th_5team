package depromeet.ohgzoo.iam.sharing;

import depromeet.ohgzoo.iam.jwt.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sharing")
public class SharingPostApi {
    private final SharingPostService sharingPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SharingResponse sharingPost(@Login Long memberId, @RequestBody SharingRequest request) {
        return sharingPostService.sharingPost(memberId, request);
    }

    @GetMapping("/{link}")
    public SharingPostDto getSharingPost(@PathVariable String link) {
        return sharingPostService.getSharingPost(link);
    }
}
