package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления классами студентов.
 * Предоставляет RESTful API для создания, получения, обновления и удаления классов студентов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student-class")
@Slf4j
@Tag(name = "Student Class", description = "APIs for managing student classes")
public class StudentClassController {

    private final StudentClassService studentClassService;

    /**
     * Создает новый класс студентов.
     *
     * @param studentClassDto DTO с данными класса студентов.
     * @return ResponseEntity с сообщением об успешном создании класса студентов.
     */
    @Operation(summary = "Create a new student class", description = "Create a new student class in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student class created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Response<StudentClassDto>> create(@RequestBody StudentClassDto studentClassDto) {
        log.info("[#createStudentClass] is calling");
        try {
            studentClassService.create(studentClassDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Student class is created successfully", studentClassDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает класс студентов по его ID.
     *
     * @param id ID класса студентов, который нужно получить.
     * @return ResponseEntity с найденным классом студентов.
     */
    @Operation(summary = "Get student class by ID", description = "Retrieve a student class by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student class found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student class not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<StudentClass>> getById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        try {
            StudentClass studentClass = studentClassService.getById(id);  // Получение сущности напрямую
            return ResponseEntity.ok(new Response<>("Student class found", studentClass));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
    }

    /**
     * Получает список всех классов студентов.
     *
     * @return ResponseEntity со списком классов студентов.
     */
    @Operation(summary = "Get all student classes", description = "Retrieve a list of all student classes")
    @ApiResponse(responseCode = "200", description = "List of student classes retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping
    public ResponseEntity<Response<List<StudentClassDto>>> getAll() {
        log.info("[#getAllGrades] is calling");
        Response<List<StudentClassDto>> response = studentClassService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет класс студентов по его ID.
     *
     * @param studentClassDto DTO с новыми данными класса студентов.
     * @param id ID класса студентов, который нужно обновить.
     * @return ResponseEntity с обновленным классом студентов.
     */
    @Operation(summary = "Update student class by ID", description = "Update an existing student class by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student class updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Student class not found", content = @Content)
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<StudentClassDto>> update(@RequestBody StudentClassDto studentClassDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<StudentClassDto> response = studentClassService.update(studentClassDto, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет класс студентов по его ID.
     *
     * @param id ID класса студентов, который нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении класса студентов.
     */
    @Operation(summary = "Delete student class by ID", description = "Remove a student class by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student class deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student class not found", content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        studentClassService.delete(id);
        return ResponseEntity.ok(new Response<>("Student class deleted successfully!", "ID: " + id));
    }
}
