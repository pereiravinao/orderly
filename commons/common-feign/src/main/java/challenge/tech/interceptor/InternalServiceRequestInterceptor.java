package challenge.tech.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class InternalServiceRequestInterceptor implements RequestInterceptor {

    @Value("${internal.service.key}")
    private String internalServiceKey;

    private static final String INTERNAL_SERVICE_HEADER = "X-Internal-Service-Key";

    @Override
    public void apply(RequestTemplate template) {
        template.header(INTERNAL_SERVICE_HEADER, internalServiceKey);
    }
}
