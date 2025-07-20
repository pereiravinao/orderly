package challenge.tech.usecase;

import challenge.tech.domain.Product;
import challenge.tech.gateway.database.ProductJpaGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllUseCaseTest {

    @Mock
    private ProductJpaGateway productJpaGateway;

    @InjectMocks
    private FindAllUseCase findAllUseCase;

    @Test
    void shouldFindAllProductsSuccessfully() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> expectedPage = new PageImpl<>(Collections.singletonList(new Product()), pageable, 1);

        when(productJpaGateway.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<Product> result = findAllUseCase.execute(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(expectedPage, result);
    }
}
