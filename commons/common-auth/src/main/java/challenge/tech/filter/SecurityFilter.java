package challenge.tech.filter;

import challenge.tech.dto.UserAuthDTO;
import challenge.tech.services.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = getToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        var user = extractUser(token);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            Collection<SimpleGrantedAuthority> authorities = Collections.emptyList();
            if (user.getRoles() != null) {
                authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    private UserAuthDTO extractUser(String token) {
        return this.jwtTokenService.decodeToken(token);
    }

}
