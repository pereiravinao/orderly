package challenge.tech.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        String errorMessage = "Erro na comunicação com o serviço externo.";

        try {
            if (response.body() != null) {
                String body = new String(response.body().asInputStream().readAllBytes());
                Map<String, Object> errorResponse = objectMapper.readValue(body, Map.class);
                if (errorResponse.containsKey("message")) {
                    errorMessage = (String) errorResponse.get("message");
                }
            }
        } catch (IOException e) {
            // Log the exception if needed
        }

        if (responseStatus.is4xxClientError() || responseStatus.is5xxServerError()) {
            return new ResponseStatusException(responseStatus, errorMessage);
        }
        return new ErrorDecoder.Default().decode(methodKey, response);
    }
}
