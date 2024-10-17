package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Review", description = "APIs for managing reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Создает новый отзыв.
     *
     * @param reviewDto DTO с данными отзыва.
     * @return ResponseEntity с кодом состояния 200 (OK) при успешном создании.
     * @throws AccountNotFoundException если аккаунт, к которому относится отзыв, не найден.
     */
    @Operation(summary = "Create a new review", description = "Add a new review to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    })
    // @PreAuthorize("hasAnyRole('DIRECTOR')")
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
    @Operation(summary = "Update review by ID", description = "Update an existing review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    // @PreAuthorize("hasAnyRole('DIRECTOR')")
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
    @Operation(summary = "Delete review by ID", description = "Remove a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    // @PreAuthorize("hasAnyRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reviewService.delete(id));
    }

    /**
     * Получает список всех отзывов.
     *
     * @return ResponseEntity со списком отзывов.
     */
    @Operation(summary = "Get all reviews", description = "Retrieve a list of all reviews")
    @ApiResponse(responseCode = "200", description = "List of reviews retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @PreAuthorize("hasAnyRole('DIRECTOR')")
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
    @Operation(summary = "Get review by ID", description = "Retrieve a review by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<ReviewDto>> getById(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(reviewService.getById(id));
    }
}
