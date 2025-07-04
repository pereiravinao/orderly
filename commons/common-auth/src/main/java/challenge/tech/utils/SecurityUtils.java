package challenge.tech.utils;

import challenge.tech.dto.UserAuthDTO;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserAuthDTO getCurrentUser() {
        return (UserAuthDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAdmin() {
        return getCurrentUser().getRoles().contains("ROLE_ADMIN");
    }

}
