package challange.tech.config;

import challange.tech.error.FeignErrorDecoder;
import challange.tech.interceptor.FeignAuthRequestInterceptor;
import challange.tech.interceptor.InternalServiceRequestInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignAuthRequestInterceptor();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public RequestInterceptor internalServiceRequestInterceptor() {
        return new InternalServiceRequestInterceptor();
    }
}