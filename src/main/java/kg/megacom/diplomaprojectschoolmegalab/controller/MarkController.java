package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.MarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления оценками.
 * Предоставляет RESTful API для создания, получения, обновления и удаления оценок.
 */
@RestController
@RequestMapping(value = "/marks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Mark", description = "APIs for managing marks")
public class MarkController {

    private final MarkService markService;

    /**
     * Создает новую оценку.
     *
     * @param markDto DTO с данными оценки.
     * @return ResponseEntity с сообщением об успешном создании и данными оценки.
     */
    @Operation(summary = "Create a new mark", description = "Add a new mark to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mark created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Response<MarkDto>> create(@RequestBody MarkDto markDto) {
        log.info("[#createMark] is calling");
        try {
            markService.create(markDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Mark is created successfully", markDto));
        } catch (IllegalArgumentException e) {
            log.error("Error creating rating: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает оценку по ее ID.
     *
     * @param id ID оценки.
     * @return ResponseEntity с данными оценки.
     */
    @Operation(summary = "Get mark by ID", description = "Retrieve mark details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mark found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarkDto.class))),
            @ApiResponse(responseCode = "404", description = "Mark not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'CLASS_TEACHER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<MarkDto>> getById(@PathVariable Long id) {
        log.info("[#getMarkById] is calling");
        MarkDto markDto = markService.getById(id);
        return ResponseEntity.ok(new Response<>("Оценка успешно получена", markDto));
    }

    /**
     * Получает список всех оценок.
     *
     * @return ResponseEntity со списком оценок.
     */
    @Operation(summary = "Get all marks", description = "Retrieve a list of all marks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of marks retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'CLASS_TEACHER')")
    @GetMapping
    public ResponseEntity<Response<List<MarkDto>>> getAll() {
        log.info("[#getAllMarks] is calling");
        Response<List<MarkDto>> response = markService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию об оценке по ее ID.
     *
     * @param id ID оценки, которую нужно обновить.
     * @param markDto DTO с новыми данными оценки.
     * @return ResponseEntity с обновленной информацией об оценке.
     */
    @Operation(summary = "Update mark by ID", description = "Update an existing mark by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mark updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MarkDto.class))),
            @ApiResponse(responseCode = "404", description = "Mark not found", content = @Content)
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<MarkDto>> update(@PathVariable Long id, @RequestBody MarkDto markDto) {
        log.info("[#updateMark] is calling");
        Response<MarkDto> response = markService.update(id, markDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет оценку по ее ID.
     *
     * @param id ID оценки, которую нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @Operation(summary = "Delete mark by ID", description = "Remove a mark by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mark deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Mark not found", content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#deleteMark] is calling");
        markService.delete(id);
        return ResponseEntity.ok(new Response<>("Mark deleted successfully!", "ID: " + id));
    }
}
