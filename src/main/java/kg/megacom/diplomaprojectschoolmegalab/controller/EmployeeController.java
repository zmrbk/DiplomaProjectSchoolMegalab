package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/employee")
@Slf4j
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    @PostMapping(value = "/create")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
        log.info("[#createEmployee] is calling");
        employeeService.createEmployee(employeeCreateRequest);
        return ResponseEntity.ok("Employee created");
    }

    @GetMapping(value = "/get-employee-by-id/{id}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable Long id) {
        log.info("[#getEmployeeById] is calling");
        Response response = employeeService.findEmployeeById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/get-all-employee")
    public ResponseEntity<Response> getAllEmployees() {
        log.info("[#getAllEmployees] is calling");
        Response response = employeeService.getAllEmployees();
        return ResponseEntity.ok(response);
    }
    @PostMapping(value = "/update-employee/{id}")
    public ResponseEntity<Response> updateEmployee(@RequestBody EmployeeCreateRequest employeeCreateRequest, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response response = employeeService.updateEmployee(employeeCreateRequest, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable Long id) {
        log.info("[#delete] is calling");
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new Response("Deleted!", "ID: " + id));
    }
}
