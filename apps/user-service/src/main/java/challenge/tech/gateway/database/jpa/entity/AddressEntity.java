package challenge.tech.gateway.database.jpa.entity;

import challenge.tech.domain.Address;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public AddressEntity(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.complement = address.getComplement();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
        this.createdAt = address.getCreatedAt();
        this.user = address.getUser() != null ? new UserEntity(address.getUser()) : null;
    }

    public Address toDomain() {
        Address address = new Address();
        address.setId(this.id);
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setComplement(this.complement);
        address.setCity(this.city);
        address.setState(this.state);
        address.setZipCode(this.zipCode);
        address.setCreatedAt(this.createdAt);
        return address;
    }

}
