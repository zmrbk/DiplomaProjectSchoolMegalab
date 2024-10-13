package kg.megacom.diplomaprojectschoolmegalab.exceptions;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Response<String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Response<>(ex.getMessage(), "Error"));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Response<String>> handleRoleAlreadyExistsException(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(ex.getMessage(), "Error"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", "Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
