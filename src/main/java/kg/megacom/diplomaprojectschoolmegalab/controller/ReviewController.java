package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
/**
 * Контроллер для управления отзывами.
 * Предоставляет RESTful API для создания, получения, обновления и удаления отзывов.
 */
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Создает новый отзыв.
     *
     * @param reviewDto DTO с данными отзыва.
     * @return ResponseEntity с кодом состояния 200 (OK) при успешном создании.
     * @throws AccountNotFoundException если аккаунт, к которому относится отзыв, не найден.
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ReviewDto reviewDto) throws AccountNotFoundException {
        try {
            reviewService.create(reviewDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Обновляет отзыв по его ID.
     *
     * @param reviewDto DTO с новыми данными отзыва.
     * @param id ID отзыва, который нужно обновить.
     * @return ResponseEntity с обновленными данными отзыва.
     * @throws AccountNotFoundException если аккаунт, к которому относится отзыв, не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<ReviewDto>> update(@RequestBody ReviewDto reviewDto, @PathVariable Long id) throws AccountNotFoundException {
        return ResponseEntity.ok(reviewService.update(reviewDto, id));
    }

    /**
     * Удаляет отзыв по его ID.
     *
     * @param id ID отзыва, который нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     * @throws EntityNotFoundException если отзыв с указанным ID не найден.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reviewService.delete(id));
    }

    /**
     * Получает список всех отзывов.
     *
     * @return ResponseEntity со списком отзывов.
     */
    @GetMapping
    public ResponseEntity<Response<List<ReviewDto>>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    /**
     * Получает отзыв по его ID.
     *
     * @param id ID отзыва.
     * @return ResponseEntity с данными отзыва.
     * @throws EntityNotFoundException если отзыв с указанным ID не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<ReviewDto>> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reviewService.getById(id));
    }
}
