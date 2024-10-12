package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.LessonDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public ResponseEntity<Response<Void>> create(@RequestBody LessonDto lessonDto) {
        log.info("[#createLesson] is calling");
        lessonService.create(lessonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Lesson created successfully", null));
    }

    @GetMapping
    public ResponseEntity<Response<List<LessonDto>>> getAll() {
        log.info("[#getAllLessons] is calling");
        Response<List<LessonDto>> response = lessonService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<LessonDto>> getById(@PathVariable Long id) {
        log.info("[#getLessonById] is calling");
        LessonDto lessonDto = lessonService.getById(id);
        return ResponseEntity.ok(new Response<>("Lesson found", lessonDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<LessonDto>> update(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        log.info("[#updateLesson] is calling");
        Response<LessonDto> response = lessonService.update(id, lessonDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        log.info("[#deleteLesson] is calling");
        lessonService.delete(id);
        return ResponseEntity.ok(new Response<>("Lesson deleted successfully", null));
    }
}
