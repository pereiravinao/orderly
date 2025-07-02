package challange.tech.services;

import challange.tech.dto.UserAuthDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    public String generateToken(UserAuthDTO userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserAuthDTO user) {
            if (user.getRoles() != null) {
                Set<String> roleNames = new HashSet<>(user.getRoles());
                claims.put("roles", roleNames);
            }
            claims.put("cpf", user.getCpf());
            claims.put("email", user.getEmail());
            claims.put("id", user.getId());
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public UserAuthDTO decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        var email = claims.get("email");
        var cpf = claims.get("cpf");
        var id = claims.get("id");
        var roles = claims.get("roles");

        return UserAuthDTO.builder()
                .email(email.toString())
                .id(Long.valueOf(id.toString()))
                .cpf(cpf.toString())
                .roles(((List<?>) roles).stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet()))
                .build();
    }

//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException e) {
//            throw new RuntimeException("Token is expired or malformed", e);
//        }
//    }

}
