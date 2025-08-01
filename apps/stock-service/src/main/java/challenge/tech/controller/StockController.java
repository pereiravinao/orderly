package challenge.tech.controller;

import challenge.tech.domain.Stock;
import challenge.tech.dto.parameter.CreateStockParameter;
import challenge.tech.dto.parameter.UpdateStockParameter;
import challenge.tech.usecase.CreateUseCase;
import challenge.tech.usecase.DeleteUseCase;
import challenge.tech.usecase.FindAllUseCase;
import challenge.tech.usecase.FindByProductsIdUseCase;
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
@RequestMapping("/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final FindAllUseCase findAllUseCase;
    private final FindByProductsIdUseCase findByProductsIdUseCase;
    private final CreateUseCase createUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteUseCase deleteUseCase;

    @GetMapping
    public ResponseEntity<Page<Stock>> findAllUsers(@PageableDefault Pageable pageable) {
        var stocks = findAllUseCase.execute(pageable);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Stock> findByProductsId(@PathVariable Long productId) {
        var stock = findByProductsIdUseCase.execute(productId);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Stock> create(@Valid @RequestBody CreateStockParameter parameter) {
        var stock = createUseCase.execute(parameter.toDomain());
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody UpdateStockParameter parameter) {
        var stock = updateUseCase.execute(id, parameter.getProductId(), parameter.getQuantity());
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

