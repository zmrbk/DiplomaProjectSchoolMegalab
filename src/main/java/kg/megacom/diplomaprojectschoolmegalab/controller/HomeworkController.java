package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.HomeworkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.HomeworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/homeworks")
@RequiredArgsConstructor
@Slf4j
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping
    public ResponseEntity<Response<Void>> createHomework(@RequestBody HomeworkDto homeworkDto) {
        log.info("[#createHomework] is calling");
        homeworkService.create(homeworkDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Homework created successfully", null));
    }

    @GetMapping
    public ResponseEntity<Response<List<HomeworkDto>>> getAllHomeworks() {
        log.info("[#getAllHomeworks] is calling");
        Response<List<HomeworkDto>> response = homeworkService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<HomeworkDto>> getHomeworkById(@PathVariable Long id) {
        log.info("[#getHomeworkById] is calling with ID: {}", id);
        HomeworkDto homeworkDto = homeworkService.getById(id);
        return ResponseEntity.ok(new Response<>("Homework found", homeworkDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<HomeworkDto>> updateHomework(@PathVariable Long id, @RequestBody HomeworkDto homeworkDto) {
        log.info("[#updateHomework] is calling with ID: {}", id);
        Response<HomeworkDto> response = homeworkService.update(id, homeworkDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteHomework(@PathVariable Long id) {
        log.info("[#deleteHomework] is calling with ID: {}", id);
        homeworkService.delete(id);
        return ResponseEntity.ok(new Response<>("Homework deleted successfully", null));
    }
}

