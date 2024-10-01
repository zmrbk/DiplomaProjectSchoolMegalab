package kg.megacom.diplomaprojectschoolmegalab.controller;


import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student-class")
@Slf4j
public class StudentClassController {

    private final StudentClassService studentClassService;

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody StudentClassDto studentClassDto) {
        log.info("[#createGrade] is calling");
        try {
            studentClassService.create(studentClassDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response("Student Class is created", studentClassDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response("Invalid input", e.getMessage()));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        Response response = studentClassService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        log.info("[#getAllGrades] is calling");
        Response response = studentClassService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> update(@RequestBody StudentClassDto studentClassDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response response = studentClassService.update(studentClassDto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        studentClassService.delete(id);
        return ResponseEntity.ok(new Response("Deleted!", "ID: " + id));
    }
}
