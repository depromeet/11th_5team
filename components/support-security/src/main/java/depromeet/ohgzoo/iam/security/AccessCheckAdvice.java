package depromeet.ohgzoo.iam.security;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@RequiredArgsConstructor
public class AccessCheckAdvice {

    @Pointcut("execution(* depromeet.ohgzoo.iam.security..*.*(..))")
    private void test() {
    }

    @Before("test() && args(post, userId,..)")
    public void security(Post post, Long userId) {
        if (!post.getMember().getId().equals(userId)) {
            throw new AccessDeniedException();
        }
    }
}
