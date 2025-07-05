package challenge.tech.controller;

import challenge.tech.dto.OrderReceiverDTO;
import challenge.tech.usecase.ProcessOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders-receivers")
@RequiredArgsConstructor
public class OrderReceiverController {

    private final ProcessOrderUseCase processOrderUseCase;

    @PostMapping
    public ResponseEntity<Void> receiveOrder(@RequestBody OrderReceiverDTO orderReceiverDTO) {
        processOrderUseCase.execute(orderReceiverDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
