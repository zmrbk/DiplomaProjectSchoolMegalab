package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления сотрудниками.
 * Предоставляет RESTful API для создания, получения, обновления и удаления сотрудников.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
@Slf4j
@Tag(name = "Employee", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    /**
     * Создает нового сотрудника.
     *
     * @param employeeCreateRequest DTO с данными нового сотрудника.
     * @return ResponseEntity с сообщением об успешном создании и информацией о созданном сотруднике.
     */
    @Operation(summary = "Create a new employee", description = "Add a new employee to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect input", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PostMapping
    public ResponseEntity<Response<EmployeeDto>> create(@RequestBody EmployeeDto employeeCreateRequest) {
        log.info("[#createEmployee] is calling");
        try {
            employeeService.create(employeeCreateRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Employee is created successfully", employeeCreateRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает информацию о сотруднике по его ID.
     *
     * @param id ID сотрудника.
     * @return ResponseEntity с данными сотрудника.
     */
    @Operation(summary = "Get employee by ID", description = "Retrieve employee details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> getById(@PathVariable Long id) {
        log.info("[#getEmployeeById] is calling");
        Response<EmployeeDto> response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Получает список всех сотрудников.
     *
     * @return ResponseEntity с списком сотрудников.
     */
    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDto.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @GetMapping
    public ResponseEntity<Response<List<EmployeeDto>>> getAll() {
        log.info("[#getAllEmployees] is calling");
        Response<List<EmployeeDto>> response = employeeService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию о сотруднике по его ID.
     *
     * @param employeeCreateRequest DTO с новыми данными сотрудника.
     * @param id ID сотрудника, информацию о котором нужно обновить.
     * @return ResponseEntity с обновленной информацией о сотруднике.
     */
    @Operation(summary = "Update employee details", description = "Update an existing employee's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> update(@RequestBody EmployeeDto employeeCreateRequest, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<EmployeeDto> response = employeeService.update(employeeCreateRequest, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет сотрудника по его ID.
     *
     * @param id ID сотрудника, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @Operation(summary = "Delete employee by ID", description = "Remove an employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        employeeService.delete(id);
        return ResponseEntity.ok(new Response<>("Employee deleted successfully!", "ID: " + id));
    }
}
