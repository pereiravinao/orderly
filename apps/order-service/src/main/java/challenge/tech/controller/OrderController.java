package challenge.tech.controller;

import challenge.tech.domain.Order;
import challenge.tech.dto.paremeter.CreateOrderParameter;
import challenge.tech.dto.presenter.OrderResponse;
import challenge.tech.usecase.CreateUseCase;
import challenge.tech.usecase.DeleteUseCase;
import challenge.tech.usecase.FindAllUseCase;
import challenge.tech.usecase.FindByIdUseCase;
import challenge.tech.usecase.UpdatePaymentUseCase;
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
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final FindAllUseCase findAllUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final CreateUseCase createUseCase;
    private final DeleteUseCase deleteUseCase;
    private final UpdatePaymentUseCase updatePaymentUseCase;

    @GetMapping
    public ResponseEntity<Page<Order>> findAllUsers(@PageableDefault Pageable pageable) {
        var stocks = findAllUseCase.execute(pageable);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        var stock = findByIdUseCase.execute(id);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderParameter parameter) {
        var stock = createUseCase.execute(parameter.toDomain());
        return new ResponseEntity<>(new OrderResponse(stock), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
