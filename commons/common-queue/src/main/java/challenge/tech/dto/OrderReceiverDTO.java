package challenge.tech.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderReceiverDTO {
    private Long id;
    private List<OrderItemDTO> items;
    private PaymentDTO payment;
    private OrderDTO order;
    private UserDTO user;
    private LocalDateTime createdAt;
}
