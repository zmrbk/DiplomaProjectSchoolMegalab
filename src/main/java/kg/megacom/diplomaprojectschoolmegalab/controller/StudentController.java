package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @PostMapping(value = "/create")
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

    @GetMapping(value = "/all")
    public ResponseEntity<Response<List<StudentDto>>> getAll() {
        log.info("[#getAllStudents] is calling");
        try {
            Response<List<StudentDto>> response = studentService.getAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while retrieving students", null));
        }
    }

    @PutMapping(value = "/update")
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

    @DeleteMapping(value = "/delete/{id}")
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
