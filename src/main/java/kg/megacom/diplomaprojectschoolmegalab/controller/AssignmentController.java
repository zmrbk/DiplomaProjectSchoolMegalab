package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
@Slf4j
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<Response<Void>> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        log.info("[#createAssignment] is calling");
        assignmentService.create(assignmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Assignment created successfully", null));
    }

    @GetMapping
    public ResponseEntity<Response<List<AssignmentDto>>> getAllAssignments() {
        log.info("[#getAllAssignments] is calling");
        Response<List<AssignmentDto>> response = assignmentService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AssignmentDto>> getAssignmentById(@PathVariable Long id) {
        log.info("[#getAssignmentById] is calling with ID: {}", id);
        AssignmentDto assignmentDto = assignmentService.getById(id);
        return ResponseEntity.ok(new Response<>("Assignment found", assignmentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<AssignmentDto>> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        log.info("[#updateAssignment] is calling with ID: {}", id);
        Response<AssignmentDto> response = assignmentService.update(id, assignmentDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAssignment(@PathVariable Long id) {
        log.info("[#deleteAssignment] is calling with ID: {}", id);
        assignmentService.delete(id);
        return ResponseEntity.ok(new Response<>("Assignment deleted successfully", null));
    }
}
