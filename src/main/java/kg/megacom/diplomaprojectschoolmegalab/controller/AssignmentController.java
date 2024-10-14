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

/**
 * AssignmentController - это REST-контроллер, который управляет заданиями
 * в системе управления школой. Он предоставляет конечные точки для создания,
 * получения, обновления и удаления заданий.
 *
 * <p>Контроллер использует {@link AssignmentService} для выполнения
 * бизнес-логики, связанной с заданиями.</p>
 */
@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
@Slf4j
public class AssignmentController {

    private final AssignmentService assignmentService;

    /**
     * Создает новое задание.
     *
     * @param assignmentDto объект передачи данных, содержащий детали задания
     * @return ResponseEntity с сообщением об успехе
     */
    @PostMapping
    public ResponseEntity<Response<Void>> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        log.info("[#createAssignment] is calling");
        assignmentService.create(assignmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Задание успешно создано", null));
    }

    /**
     * Получает все задания.
     *
     * @return ResponseEntity со списком всех заданий
     */
    @GetMapping
    public ResponseEntity<Response<List<AssignmentDto>>> getAllAssignments() {
        log.info("[#getAllAssignments] is calling");
        Response<List<AssignmentDto>> response = assignmentService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает задание по его идентификатору.
     *
     * @param id идентификатор задания
     * @return ResponseEntity с данными задания
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<AssignmentDto>> getAssignmentById(@PathVariable Long id) {
        log.info("[#getAssignmentById] is calling with ID: {}", id);
        AssignmentDto assignmentDto = assignmentService.getById(id);
        return ResponseEntity.ok(new Response<>("Задание найдено", assignmentDto));
    }

    /**
     * Обновляет существующее задание.
     *
     * @param id идентификатор задания для обновления
     * @param assignmentDto обновленные данные задания
     * @return ResponseEntity с обновленным заданием
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<AssignmentDto>> updateAssignment(@PathVariable Long id, @RequestBody AssignmentDto assignmentDto) {
        log.info("[#updateAssignment] is calling with ID: {}", id);
        Response<AssignmentDto> response = assignmentService.update(id, assignmentDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет задание по его идентификатору.
     *
     * @param id идентификатор задания для удаления
     * @return ResponseEntity с сообщением об успехе
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAssignment(@PathVariable Long id) {
        log.info("[#deleteAssignment] is calling with ID: {}", id);
        assignmentService.delete(id);
        return ResponseEntity.ok(new Response<>("Задание успешно удалено", null));
    }
}
