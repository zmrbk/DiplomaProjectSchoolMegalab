package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
        log.info("[#createEmployee] is calling");
        try {
            employeeService.create(employeeCreateRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response("Employee is created: ", employeeCreateRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response("Invalid input", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        log.info("[#getEmployeeById] is calling");
        Response response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<Response> getAll() {
        log.info("[#getAllEmployees] is calling");
        Response response = employeeService.getAll();
        return ResponseEntity.ok(response);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> update(@RequestBody EmployeeCreateRequest employeeCreateRequest, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response response = employeeService.update(employeeCreateRequest, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        employeeService.delete(id);
        return ResponseEntity.ok(new Response("Deleted!", "ID: " + id));
    }
}
