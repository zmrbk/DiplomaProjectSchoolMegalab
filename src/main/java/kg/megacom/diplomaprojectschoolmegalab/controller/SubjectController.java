package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.SubjectServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления предметами.
 * Предоставляет RESTful API для создания, получения, обновления и удаления предметов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/subjects")
@Slf4j
@Tag(name = "Subject", description = "APIs for managing subjects")
public class SubjectController {

    private final SubjectServiceImpl subjectService;

    /**
     * Создает новый предмет.
     *
     * @param subjectsDto DTO с данными предмета.
     * @return ResponseEntity с сообщением об успешном создании предмета.
     */
    @Operation(summary = "Create a new subject", description = "Create a new subject in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subject created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectsDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @PostMapping
    public ResponseEntity<Response<SubjectsDto>> create(@RequestBody SubjectsDto subjectsDto) {
        log.info("[#create] is calling");
        try {
            subjectService.create(subjectsDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Subject is created successfully", subjectsDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает предмет по его ID.
     *
     * @param id ID предмета, который нужно получить.
     * @return ResponseEntity с найденным предметом.
     */
    @Operation(summary = "Get subject by ID", description = "Retrieve a subject by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectsDto.class))),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER', 'STUDENTS')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<SubjectsDto>> getById(@PathVariable Long id) {
        log.info("[#getById] is calling");
        Response<SubjectsDto> response = subjectService.getById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Получает список всех предметов.
     *
     * @return ResponseEntity со списком предметов.
     */
    @Operation(summary = "Get all subjects", description = "Retrieve a list of all subjects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of subjects retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping
    public ResponseEntity<Response<List<SubjectsDto>>> getAll() {
        log.info("[#getAll] is calling");
        Response<List<SubjectsDto>> response = subjectService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию о предмете.
     *
     * @param subjectsDto DTO с новыми данными предмета.
     * @param id ID предмета, который нужно обновить.
     * @return ResponseEntity с обновленным предметом.
     */
    @Operation(summary = "Update subject information", description = "Update an existing subject's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubjectsDto.class))),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<SubjectsDto>> update(@RequestBody SubjectsDto subjectsDto, @PathVariable Long id) {
        log.info("[#update] is calling");
        Response<SubjectsDto> response = subjectService.update(subjectsDto, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет предмет по его ID.
     *
     * @param id ID предмета, который нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении предмета.
     */
    @Operation(summary = "Delete subject by ID", description = "Remove a subject by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        subjectService.delete(id);
        return ResponseEntity.ok(new Response<>("Subject deleted successfully!", "ID: " + id));
    }
}
