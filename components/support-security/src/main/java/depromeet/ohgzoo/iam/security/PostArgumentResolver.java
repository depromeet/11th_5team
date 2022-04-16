package depromeet.ohgzoo.iam.security;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PostArgumentResolver implements HandlerMethodArgumentResolver {

    private final PostRepository postRepository;

    public PostArgumentResolver(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(PostEntity.class);
        boolean hasPostType = Post.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasPostType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String postId = pathVariables.get("postid");
        Post post = postRepository.findById(Long.valueOf(postId)).get();

        return post;
    }
}
