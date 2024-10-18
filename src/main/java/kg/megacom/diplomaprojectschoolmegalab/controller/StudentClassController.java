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

/**
 * Контроллер для управления классами студентов.
 * Предоставляет RESTful API для создания, получения, обновления и удаления классов студентов.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student-class")
@Slf4j
public class StudentClassController {

    private final StudentClassService studentClassService;

    /**
     * Создает новый класс студентов.
     *
     * @param studentClassDto DTO с данными класса студентов.
     * @return ResponseEntity с сообщением об успешном создании класса студентов.
     */
    @PostMapping
    public ResponseEntity<Response<StudentClassDto>> create(@RequestBody StudentClassDto studentClassDto) {
        log.info("[#createStudentClass] is calling");
        try {
            studentClassService.create(studentClassDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Student class is created successfully", studentClassDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает класс студентов по его ID.
     *
     * @param id ID класса студентов, который нужно получить.
     * @return ResponseEntity с найденным классом студентов.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<StudentClass>> getById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        try {
            StudentClass studentClass = studentClassService.getById(id);  // Получение сущности напрямую
            return ResponseEntity.ok(new Response<>("Student class found", studentClass));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        }
    }

    /**
     * Получает список всех классов студентов.
     *
     * @return ResponseEntity со списком классов студентов.
     */
    @GetMapping
    public ResponseEntity<Response<List<StudentClassDto>>> getAll() {
        log.info("[#getAllGrades] is calling");
        Response<List<StudentClassDto>> response = studentClassService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет класс студентов по его ID.
     *
     * @param studentClassDto DTO с новыми данными класса студентов.
     * @param id ID класса студентов, который нужно обновить.
     * @return ResponseEntity с обновленным классом студентов.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<StudentClassDto>> update(@RequestBody StudentClassDto studentClassDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<StudentClassDto> response = studentClassService.update(studentClassDto, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет класс студентов по его ID.
     *
     * @param id ID класса студентов, который нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении класса студентов.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        studentClassService.delete(id);
        return ResponseEntity.ok(new Response<>("Student class deleted successfully!", "ID: " + id));
    }
}
