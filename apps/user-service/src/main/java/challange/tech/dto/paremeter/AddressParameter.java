package challange.tech.dto.paremeter;

import challange.tech.domain.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressParameter {
    @NotBlank(message = "Nome da rua é obrigatório")
    private String street;

    @NotBlank(message = "Número é obrigatório")
    private String number;

    private String complement;

    @NotBlank(message = "Cidade é obrigatória")
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    private String state;

    @NotBlank(message = "CEP é obrigatório")
    private String zipCode;

    public Address toDomain() {
        Address address = new Address();
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setComplement(this.complement);
        address.setCity(this.city);
        address.setState(this.state);
        address.setZipCode(this.zipCode);
        return address;
    }
}

