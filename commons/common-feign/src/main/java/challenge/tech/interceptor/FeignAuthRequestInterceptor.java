package challenge.tech.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class FeignAuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            final var request = attributes.getRequest();
            final var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(authorization)) {
                template.header(HttpHeaders.AUTHORIZATION, authorization);
            }
        }
    }
}
