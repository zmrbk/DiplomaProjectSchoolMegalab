package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления родителями.
 * Предоставляет RESTful API для создания, получения, обновления и удаления родителей.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/parents")
@Slf4j
@Tag(name = "Parent", description = "APIs for managing parents")
public class ParentController {

    private final ParentServiceImpl parentService;

    /**
     * Создает нового родителя.
     *
     * @param parentDto DTO с данными родителя.
     * @return ResponseEntity с информацией о созданном родителе.
     */
    @Operation(summary = "Create a new parent", description = "Add a new parent to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parent created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParentDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PostMapping
    public ResponseEntity<Response<ParentDto>> create(@RequestBody ParentDto parentDto) {
        log.info("[#createParent] is calling");
        try {
            parentService.create(parentDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Parent is created successfully", parentDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает родителя по его ID.
     *
     * @param id ID родителя.
     * @return ResponseEntity с данными родителя.
     */
    @Operation(summary = "Get parent by ID", description = "Retrieve parent details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParentDto.class))),
            @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> getById(@PathVariable Long id) {
        log.info("[#getParentById] is calling");
        Response<ParentDto> response = parentService.getParentDtoById(id); // Вызов нового метода
        return ResponseEntity.ok(response);
    }

    /**
     * Получает список всех родителей.
     *
     * @return ResponseEntity со списком родителей.
     */
    @Operation(summary = "Get all parents", description = "Retrieve a list of all parents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of parents retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "No parents found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<ParentDto>>> getAll() {
        log.info("[#getAll] is calling");
        Response<List<ParentDto>> response = parentService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию о родителе по его ID.
     *
     * @param parentDto DTO с новыми данными родителя.
     * @param id ID родителя, которого нужно обновить.
     * @return ResponseEntity с обновленной информацией о родителе.
     */
    @Operation(summary = "Update parent by ID", description = "Update an existing parent by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParentDto.class))),
            @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> update(@RequestBody ParentDto parentDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<ParentDto> response = parentService.update(parentDto, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет родителя по его ID.
     *
     * @param id ID родителя, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @Operation(summary = "Delete parent by ID", description = "Remove a parent by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parent deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Parent not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        parentService.delete(id);
        return ResponseEntity.ok(new Response<>("Parent deleted successfully!", "ID: " + id));
    }
}
