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
/**
 * Контроллер для управления уроками.
 * Предоставляет RESTful API для создания, получения, обновления и удаления уроков.
 */
@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
@Slf4j
public class LessonController {

    private final LessonService lessonService;

    /**
     * Создает новый урок.
     *
     * @param lessonDto DTO с данными урока.
     * @return ResponseEntity с сообщением об успешном создании.
     */
    @PostMapping
    public ResponseEntity<Response<Void>> create(@RequestBody LessonDto lessonDto) {
        log.info("[#createLesson] is calling");
        lessonService.create(lessonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Урок успешно создан", null));
    }

    /**
     * Получает список всех уроков.
     *
     * @return ResponseEntity со списком уроков.
     */
    @GetMapping
    public ResponseEntity<Response<List<LessonDto>>> getAll() {
        log.info("[#getAllLessons] is calling");
        Response<List<LessonDto>> response = lessonService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает урок по его ID.
     *
     * @param id ID урока.
     * @return ResponseEntity с данными урока.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<LessonDto>> getById(@PathVariable Long id) {
        log.info("[#getLessonById] is calling with ID: {}", id);
        LessonDto lessonDto = lessonService.getById(id);
        return ResponseEntity.ok(new Response<>("Урок найден", lessonDto));
    }

    /**
     * Обновляет информацию об уроке по его ID.
     *
     * @param id ID урока, который нужно обновить.
     * @param lessonDto DTO с новыми данными урока.
     * @return ResponseEntity с обновленной информацией об уроке.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<LessonDto>> update(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        log.info("[#updateLesson] is calling with ID: {}", id);
        Response<LessonDto> response = lessonService.update(id, lessonDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет урок по его ID.
     *
     * @param id ID урока, который нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        log.info("[#deleteLesson] is calling with ID: {}", id);
        lessonService.delete(id);
        return ResponseEntity.ok(new Response<>("Урок успешно удален", null));
    }
}
