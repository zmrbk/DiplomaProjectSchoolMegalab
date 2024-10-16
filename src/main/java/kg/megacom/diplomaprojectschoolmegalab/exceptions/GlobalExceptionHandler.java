package kg.megacom.diplomaprojectschoolmegalab.exceptions;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для обработки специфических исключений,
 * возникающих в приложении.
 * <p>
 * Этот класс позволяет централизовать обработку ошибок и возвращать соответствующие
 * HTTP-ответы в случае возникновения исключений.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link UnauthorizedAccessException}.
     *
     * @param ex Исключение, которое было выброшено.
     * @return Ответ с кодом 403 (Forbidden) и сообщением об ошибке.
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Response<String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Response<>(ex.getMessage(), "Error"));
    }

    /**
     * Обрабатывает исключение {@link EntityAlreadyExistsException}.
     *
     * @param ex Исключение, которое было выброшено.
     * @return Ответ с кодом 400 (Bad Request) и сообщением об ошибке.
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Response<String>> handleRoleAlreadyExistsException(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response<>(ex.getMessage(), "Error"));
    }

    /**
     * Обрабатывает исключение {@link EntityNotFoundException}.
     *
     * @param ex Исключение, которое было выброшено.
     * @return Ответ с кодом 404 (Not Found) и сообщением об ошибке в виде карты.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", "Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new Response<>("You have not permission!", "Error"));
    }
}
