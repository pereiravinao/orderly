package challange.tech.controller;

import challange.tech.domain.Address;
import challange.tech.dto.paremeter.AddressParameter;
import challange.tech.usecase.address.CreateAddressUseCase;
import challange.tech.usecase.address.DeleteAddressUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final CreateAddressUseCase createAddressUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;

    @PostMapping
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressParameter request) {
        var addressCreated = createAddressUseCase.execute(request.toDomain());
        return new ResponseEntity<>(addressCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        deleteAddressUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
