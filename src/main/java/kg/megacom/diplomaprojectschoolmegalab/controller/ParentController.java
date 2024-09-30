package kg.megacom.diplomaprojectschoolmegalab.controller;


import jakarta.servlet.ServletResponse;
import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.ParentService;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.EmployeeServiceImpl;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/parents")
@Slf4j
public class ParentController {

    private final ParentServiceImpl parentService;

    @PostMapping(value = "/create")
    public ResponseEntity<Response> createEmployee(@RequestBody ParentDto parentDto) {
        log.info("[#createParent] is calling");
        try {
            return ResponseEntity.ok(parentService.createParent(parentDto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping(value = "/get-parent-by-id/{id}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable Long id) {
        log.info("[#getParentById] is calling");
        Response response = parentService.getParentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/get-all-parents")
    public ResponseEntity<Response> getAllEmployees(ServletResponse servletResponse) {
        log.info("[#getAllParents] is calling");
        Response response = parentService.getAllParents();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/update-parent/{id}")
    public ResponseEntity<Response> updateEmployee(@RequestBody ParentDto parentDto, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response response = parentService.updateParent(parentDto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable Long id) {
        log.info("[#delete] is calling");
        parentService.deleteParent(id);
        return ResponseEntity.ok(new Response("Deleted!", "ID: " + id));
    }
}
