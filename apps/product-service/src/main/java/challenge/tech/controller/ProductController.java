package challenge.tech.controller;

import challenge.tech.domain.Product;
import challenge.tech.dto.paremeter.CreateProductParameter;
import challenge.tech.usecase.CreateUseCase;
import challenge.tech.usecase.DeleteUseCase;
import challenge.tech.usecase.FindAllUseCase;
import challenge.tech.usecase.FindByIdUseCase;
import challenge.tech.usecase.UpdateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final FindAllUseCase findAllUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final CreateUseCase createUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteUseCase deleteUseCase;

    @GetMapping
    public ResponseEntity<Page<Product>> findAllUsers(@PageableDefault Pageable pageable) {
        var products = findAllUseCase.execute(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        var product = findByIdUseCase.execute(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> create(@Valid @RequestBody CreateProductParameter parameter) {
        var product = createUseCase.execute(parameter.toDomain());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product parameter) {
        var product = updateUseCase.execute(id, parameter);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
