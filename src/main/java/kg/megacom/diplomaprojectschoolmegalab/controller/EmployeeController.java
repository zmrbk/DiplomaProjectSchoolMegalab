package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    /**
     * Создает нового сотрудника.
     *
     * @param employeeCreateRequest DTO с данными нового сотрудника.
     * @return ResponseEntity с сообщением об успешном создании и информацией о созданном сотруднике.
     */
    @PostMapping
    public ResponseEntity<Response<EmployeeDto>> create(@RequestBody EmployeeDto employeeCreateRequest) {
        log.info("[#createEmployee] is calling");
        try {
            employeeService.create(employeeCreateRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Сотрудник создан: ", employeeCreateRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Недопустимый ввод", null));
        }
    }

    /**
     * Получает информацию о сотруднике по его ID.
     *
     * @param id ID сотрудника.
     * @return ResponseEntity с данными сотрудника.
     */
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        employeeService.delete(id);
        return ResponseEntity.ok(new Response<>("Удалено!", "ID: " + id));
    }
}

