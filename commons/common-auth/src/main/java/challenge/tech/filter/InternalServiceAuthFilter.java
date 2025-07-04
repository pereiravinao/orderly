package challenge.tech.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class InternalServiceAuthFilter extends OncePerRequestFilter {

    @Value("${internal.service.key}")
    private String internalServiceKey;

    private static final String INTERNAL_SERVICE_HEADER = "X-Internal-Service-Key";
    private static final String INTERNAL_SERVICE_ROLE = "ROLE_INTERNAL_SERVICE";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(INTERNAL_SERVICE_HEADER);

        if (header != null && header.equals(internalServiceKey)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    "internal-service",
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority(INTERNAL_SERVICE_ROLE))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
