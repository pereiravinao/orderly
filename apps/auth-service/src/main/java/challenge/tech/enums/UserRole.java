package challenge.tech.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    EMPLOYEE("EMPLOYEE"),
    CLIENT("CLIENT"),
    USER("USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
