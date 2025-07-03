package challange.tech.exceptions;

import challange.tech.exceptions.auth.AuthExceptionHandler;
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
                .body(getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
                        ex.getStackTrace().toString()));
    }

    @ExceptionHandler(AuthExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage(), ex.getStackTrace().toString()));
    }

    @ExceptionHandler(UserExceptionHandler.class)
    public ResponseEntity<Map<String, Object>> handleUserException(UserExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(getErrorResponse(ex.getStatus(), ex.getMessage(), ex.getStackTrace().toString()));
    }


    private Map<String, Object> getErrorResponse(HttpStatus status, String message, String stackTrace) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StringBuilder detailedStackTrace = new StringBuilder();
        detailedStackTrace.append("Local do erro:\n");

        for (StackTraceElement element : stackTraceElements) {
            if (element.getClassName().startsWith("com.app")) {
                // Formata cada linha do stack trace de forma mais amigável
                String className = element.getClassName().substring(element.getClassName().lastIndexOf(".") + 1);
                String methodName = element.getMethodName();
                String fileName = element.getFileName();
                int lineNumber = element.getLineNumber();

                detailedStackTrace.append("  → ")
                        .append(className)
                        .append(".")
                        .append(methodName)
                        .append(" (")
                        .append(fileName)
                        .append(":")
                        .append(lineNumber)
                        .append(")\n");
            }
        }
        errorResponse.put("stackTrace", detailedStackTrace.toString());

        errorResponse.put("timestamp", LocalDateTime.now().toString());
        return errorResponse;
    }

}
