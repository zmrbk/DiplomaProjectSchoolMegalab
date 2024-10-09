package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student-class")
@Slf4j
public class StudentClassController {

    private final StudentClassService studentClassService;

    @PostMapping
    public ResponseEntity<Response<StudentClassDto>> create(@RequestBody StudentClassDto studentClassDto) {
        log.info("[#createStudentClass] is calling");
        try {
            studentClassService.create(studentClassDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Student class is created", studentClassDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Invalid input", null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<StudentClass>> getById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        try {
            StudentClass studentClass = studentClassService.getById(id);  // Fetch the entity directly
            return ResponseEntity.ok(new Response<>("Student class found", studentClass));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<Response<List<StudentClassDto>>> getAll() {
        log.info("[#getAllGrades] is calling");
        Response<List<StudentClassDto>> response = studentClassService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<StudentClassDto>> update(@RequestBody StudentClassDto studentClassDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<StudentClassDto> response = studentClassService.update(studentClassDto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        studentClassService.delete(id);
        return ResponseEntity.ok(new Response<>("Deleted!", "ID: " + id));
    }
}
