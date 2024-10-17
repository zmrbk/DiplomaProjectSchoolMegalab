package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.LessonDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления уроками.
 * Предоставляет RESTful API для создания, получения, обновления и удаления уроков.
 */
@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Lesson", description = "APIs for managing lessons")
public class LessonController {

    private final LessonService lessonService;

    /**
     * Создает новый урок.
     *
     * @param lessonDto DTO с данными урока.
     * @return ResponseEntity с сообщением об успешном создании.
     */
    @Operation(summary = "Create a new lesson", description = "Add a new lesson to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lesson created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Response<Void>> create(@RequestBody LessonDto lessonDto) {
        log.info("[#createLesson] is calling");
        lessonService.create(lessonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Lesson is created successfully", null));
    }

    /**
     * Получает список всех уроков.
     *
     * @return ResponseEntity со списком уроков.
     */
    @Operation(summary = "Get all lessons", description = "Retrieve a list of all lessons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of lessons retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonDto.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping
    public ResponseEntity<Response<List<LessonDto>>> getAll() {
        log.info("[#getAllLessons] is calling");
        Response<List<LessonDto>> response = lessonService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает урок по его ID.
     *
     * @param id ID урока.
     * @return ResponseEntity с данными урока.
     */
    @Operation(summary = "Get lesson by ID", description = "Retrieve lesson details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonDto.class))),
            @ApiResponse(responseCode = "404", description = "Lesson not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<LessonDto>> getById(@PathVariable Long id) {
        log.info("[#getLessonById] is calling with ID: {}", id);
        LessonDto lessonDto = lessonService.getById(id);
        return ResponseEntity.ok(new Response<>("Урок найден", lessonDto));
    }

    /**
     * Обновляет информацию об уроке по его ID.
     *
     * @param id ID урока, который нужно обновить.
     * @param lessonDto DTO с новыми данными урока.
     * @return ResponseEntity с обновленной информацией об уроке.
     */
    @Operation(summary = "Update lesson by ID", description = "Update an existing lesson by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LessonDto.class))),
            @ApiResponse(responseCode = "404", description = "Lesson not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<LessonDto>> update(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        log.info("[#updateLesson] is calling with ID: {}", id);
        Response<LessonDto> response = lessonService.update(id, lessonDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет урок по его ID.
     *
     * @param id ID урока, который нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @Operation(summary = "Delete lesson by ID", description = "Remove a lesson by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lesson deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Lesson not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        log.info("[#deleteLesson] is calling with ID: {}", id);
        lessonService.delete(id);
        return ResponseEntity.ok(new Response<>("Lesson deleted successfully!", null));
    }
}
