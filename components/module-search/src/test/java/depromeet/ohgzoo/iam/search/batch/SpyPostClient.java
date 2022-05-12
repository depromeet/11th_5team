package depromeet.ohgzoo.iam.search.batch;

import java.util.Collections;
import java.util.List;

public class SpyPostClient implements PostsClient {
    public boolean getPosts_wasCalled;

    @Override
    public ResultModel<List<RemotePosts>> getPosts() {
        getPosts_wasCalled = true;
        return ResultModel.of(Collections.emptyList());
    }
}
