package challenge.tech.exceptions.stock;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StockExceptionHandlerTest {

    @Test
    void constructorShouldSetMessageAndStatus() {
        // Given
        String message = "Test message";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // When
        StockExceptionHandler exception = new StockExceptionHandler(message, status);

        // Then
        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }

    @Test
    void notFoundShouldReturnCorrectException() {
        // When
        StockExceptionHandler exception = StockExceptionHandler.notFound();

        // Then
        assertNotNull(exception);
        assertEquals("Estoque não encontrado.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void insufficientStockShouldReturnCorrectException() {
        // When
        StockExceptionHandler exception = StockExceptionHandler.insufficientStock();

        // Then
        assertNotNull(exception);
        assertEquals("Estoque insuficiente.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
