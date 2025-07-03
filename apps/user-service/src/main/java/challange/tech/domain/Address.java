package challange.tech.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Address {
    private Long id;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    private LocalDateTime createdAt;
    private User user;
}
