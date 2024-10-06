package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PostMapping
    public ResponseEntity<Response<EmployeeDto>> create(@RequestBody EmployeeDto employeeCreateRequest) {
        log.info("[#createEmployee] is calling");
        try {
            employeeService.create(employeeCreateRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Employee is created: ", employeeCreateRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Invalid input", null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> getById(@PathVariable Long id) {
        log.info("[#getEmployeeById] is calling");
        Response<EmployeeDto> response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Response<List<EmployeeDto>>> getAll() {
        log.info("[#getAllEmployees] is calling");
        Response<List<EmployeeDto>> response = employeeService.getAll();
        return ResponseEntity.ok(response);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<EmployeeDto>> update(@RequestBody EmployeeDto employeeCreateRequest, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<EmployeeDto> response = employeeService.update(employeeCreateRequest, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        employeeService.delete(id);
        return ResponseEntity.ok(new Response<>("Deleted!", "ID: " + id));
    }
}
