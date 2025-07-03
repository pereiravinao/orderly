package challange.tech.exceptions;

import challange.tech.exceptions.auth.AuthExceptionHandler;
import challange.tech.exceptions.product.ProductExceptionHandler;
import challange.tech.exceptions.stock.product.StockExceptionHandler;
import challange.tech.exceptions.user.UserExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(AuthExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(UserExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleUserException(UserExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(ProductExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleUserException(ProductExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(StockExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleUserException(StockExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage()));
    }


    private Map<String, Object> getErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        return errorResponse;
    }

}
