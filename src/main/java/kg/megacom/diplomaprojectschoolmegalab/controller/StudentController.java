package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления студентами.
 * Предоставляет RESTful API для создания, получения, обновления и удаления студентов.
 */
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student", description = "APIs for managing students")
public class StudentController {

    private final StudentService studentService;

    /**
     * Создает нового студента.
     *
     * @param studentDto DTO с данными студента.
     * @return ResponseEntity с сообщением об успешном создании студента.
     */
    @Operation(summary = "Create a new student", description = "Create a new student in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Response<StudentDto>> create(@RequestBody StudentDto studentDto) {
        log.info("[#createStudent] is calling with data: {}", studentDto);
        try {
            Response<StudentDto> response = studentService.create(studentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            log.error("An error occurred while creating the student: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while creating the student", null));
        }
    }

    /**
     * Получает студента по его ID.
     *
     * @param id ID студента, которого нужно получить.
     * @return ResponseEntity с найденным студентом.
     */
    @Operation(summary = "Get student by ID", description = "Retrieve a student by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<StudentDto>> findById(@PathVariable Long id) {
        log.info("[#getStudentById] is calling");
        try {
            Response<StudentDto> response = studentService.findById(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while retrieving the student", null));
        }
    }

    /**
     * Получает список всех студентов.
     *
     * @return ResponseEntity со списком студентов.
     */
    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of students retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER')")
    @GetMapping
    public ResponseEntity<Response<List<StudentDto>>> getAll() {
        log.info("[#getAllStudents] is calling");
        try {
            Response<List<StudentDto>> response = studentService.getAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while retrieving students", null));
        }
    }

    /**
     * Обновляет информацию о студенте.
     *
     * @param studentDto DTO с новыми данными студента.
     * @return ResponseEntity с обновленным студентом.
     */
    @Operation(summary = "Update student information", description = "Update an existing student's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDto.class))),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @PutMapping
    public ResponseEntity<Response<StudentDto>> update(@RequestBody StudentDto studentDto) {
        log.info("[#updateStudent] is calling");
        try {
            Response<StudentDto> response = studentService.update(studentDto);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while updating the student", null));
        }
    }

    /**
     * Удаляет студента по его ID.
     *
     * @param id ID студента, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении студента.
     */
    @Operation(summary = "Delete student by ID", description = "Remove a student by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "An error occurred", content = @Content)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        log.info("[#deleteStudent] is calling");
        try {
            Response<Void> response = studentService.delete(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while deleting the student", null));
        }
    }
}
