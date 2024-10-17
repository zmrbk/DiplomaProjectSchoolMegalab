package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * AssignmentController - это REST-контроллер, который управляет заданиями
 * в системе управления школой. Он предоставляет конечные точки для создания,
 * получения, обновления и удаления заданий.
 *
 * <p>Контроллер использует {@link AssignmentService} для выполнения
 * бизнес-логики, связанной с заданиями.</p>
 */
@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Assignment", description = "APIs for managing assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * Создает новое задание.
     *
     * @param assignmentDto объект передачи данных, содержащий детали задания
     * @return ResponseEntity с сообщением об успехе
     */
    @Operation(summary = "Create a new assignment", description = "Create a new assignment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Assignment created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssignmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PostMapping
    public ResponseEntity<Response<Void>> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        log.info("[#createAssignment] is calling");
        assignmentService.create(assignmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Assignment is created successfully", null));
    }

    /**
     * Получает все задания.
     *
     * @return ResponseEntity со списком всех заданий
     */
    @Operation(summary = "Get all assignments", description = "Retrieve a list of all assignments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignments retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<AssignmentDto>>> getAllAssignments() {
        log.info("[#getAllAssignments] is calling");
        Response<List<AssignmentDto>> response = assignmentService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает задание по его идентификатору.
     *
     * @param id идентификатор задания
     * @return ResponseEntity с данными задания
     */
    @Operation(summary = "Get assignment by ID", description = "Retrieve an assignment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssignmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<AssignmentDto>> getAssignmentById(@PathVariable Long id) {
        log.info("[#getAssignmentById] is calling with ID: {}", id);
        AssignmentDto assignmentDto = assignmentService.getById(id);
        return ResponseEntity.ok(new Response<>("Assignment found", assignmentDto));
    }

    /**
     * Обновляет существующее задание.
     *
     * @param id идентификатор задания для обновления
     * @param assignmentDto обновленные данные задания
     * @return ResponseEntity с обновленным заданием
     */
    @Operation(summary = "Update an assignment", description = "Update an existing assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssignmentDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict in updating the assignment", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<AssignmentDto>> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        log.info("[#updateAssignment] is calling with ID: {}", id);
        Response<AssignmentDto> response = assignmentService.update(id, assignmentDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет задание по его идентификатору.
     *
     * @param id идентификатор задания для удаления
     * @return ResponseEntity с сообщением об успехе
     */
    @Operation(summary = "Delete an assignment", description = "Delete an assignment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assignment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAssignment(@PathVariable Long id) {
        log.info("[#deleteAssignment] is calling with ID: {}", id);
        assignmentService.delete(id);
        return ResponseEntity.ok(new Response<>("Assignment deleted successfully!", null));
    }
}
