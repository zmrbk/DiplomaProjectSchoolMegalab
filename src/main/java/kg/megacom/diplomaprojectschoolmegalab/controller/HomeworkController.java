package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.HomeworkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.HomeworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления домашними заданиями.
 * Предоставляет RESTful API для создания, получения, обновления и удаления домашних заданий.
 */
@RestController
@RequestMapping("/homeworks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Homework", description = "APIs for managing homeworks")
public class HomeworkController {

    private final HomeworkService homeworkService;

    /**
     * Создает новое домашнее задание.
     *
     * @param homeworkDto DTO с данными домашнего задания.
     * @return ResponseEntity с сообщением об успешном создании.
     */
    @Operation(summary = "Create a new homework", description = "Add a new homework assignment to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Homework created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Response<Void>> createHomework(@RequestBody HomeworkDto homeworkDto) {
        log.info("[#createHomework] is calling");
        homeworkService.create(homeworkDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Homework is created successfully", null));
    }

    /**
     * Получает список всех домашних заданий.
     *
     * @return ResponseEntity со списком домашних заданий.
     */
    @Operation(summary = "Get all homeworks", description = "Retrieve a list of all homework assignments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of homeworks retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HomeworkDto.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<HomeworkDto>>> getAllHomeworks() {
        log.info("[#getAllHomeworks] is calling");
        Response<List<HomeworkDto>> response = homeworkService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает домашнее задание по его ID.
     *
     * @param id ID домашнего задания.
     * @return ResponseEntity с данными домашнего задания.
     */
    @Operation(summary = "Get homework by ID", description = "Retrieve homework details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Homework found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HomeworkDto.class))),
            @ApiResponse(responseCode = "404", description = "Homework not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<HomeworkDto>> getHomeworkById(@PathVariable Long id) {
        log.info("[#getHomeworkById] is calling with ID: {}", id);
        HomeworkDto homeworkDto = homeworkService.getById(id);
        return ResponseEntity.ok(new Response<>("Homework найдено", homeworkDto));
    }

    /**
     * Обновляет информацию о домашнем задании по его ID.
     *
     * @param id ID домашнего задания, которое нужно обновить.
     * @param homeworkDto DTO с новыми данными домашнего задания.
     * @return ResponseEntity с обновленной информацией о домашнем задании.
     */
    @Operation(summary = "Update homework by ID", description = "Update an existing homework assignment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Homework updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HomeworkDto.class))),
            @ApiResponse(responseCode = "404", description = "Homework not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<HomeworkDto>> updateHomework(@PathVariable Long id, @RequestBody HomeworkDto homeworkDto) {
        log.info("[#updateHomework] is calling with ID: {}", id);
        Response<HomeworkDto> response = homeworkService.update(id, homeworkDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет домашнее задание по его ID.
     *
     * @param id ID домашнего задания, которое нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @Operation(summary = "Delete homework by ID", description = "Remove a homework assignment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Homework deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Homework not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteHomework(@PathVariable Long id) {
        log.info("[#deleteHomework] is calling with ID: {}", id);
        homeworkService.delete(id);
        return ResponseEntity.ok(new Response<>("Homework deleted successfully!", null));
    }
}
