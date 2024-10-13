package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ScheduleDto;
import kg.megacom.diplomaprojectschoolmegalab.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления расписаниями.
 * Предоставляет RESTful API для создания, получения, обновления и удаления расписаний.
 */
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Создает новое расписание.
     *
     * @param scheduleDto DTO с данными расписания.
     * @return ResponseEntity с сообщением об успешном создании расписания.
     */
    @PostMapping
    public ResponseEntity<Response<Void>> create(@RequestBody ScheduleDto scheduleDto) {
        log.info("[#createSchedule] is calling");
        scheduleService.create(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Schedule created successfully", null));
    }

    /**
     * Получает список всех расписаний.
     *
     * @return ResponseEntity со списком расписаний.
     */
    @GetMapping
    public ResponseEntity<Response<List<ScheduleDto>>> getAll() {
        log.info("[#getAllSchedules] is calling");
        Response<List<ScheduleDto>> response = scheduleService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает расписание по его ID.
     *
     * @param id ID расписания, которое нужно получить.
     * @return ResponseEntity с найденным расписанием.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<ScheduleDto>> getById(@PathVariable Long id) {
        log.info("[#getScheduleById] is calling with ID: {}", id);
        ScheduleDto scheduleDto = scheduleService.getById(id);
        return ResponseEntity.ok(new Response<>("Schedule found", scheduleDto));
    }

    /**
     * Обновляет расписание по его ID.
     *
     * @param id ID расписания, которое нужно обновить.
     * @param scheduleDto DTO с новыми данными расписания.
     * @return ResponseEntity с обновленным расписанием.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<ScheduleDto>> update(@PathVariable Long id, @RequestBody ScheduleDto scheduleDto) {
        log.info("[#updateSchedule] is calling with ID: {}", id);
        Response<ScheduleDto> response = scheduleService.update(id, scheduleDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет расписание по его ID.
     *
     * @param id ID расписания, которое нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении расписания.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        log.info("[#deleteSchedule] is calling with ID: {}", id);
        scheduleService.delete(id);
        return ResponseEntity.ok(new Response<>("Schedule deleted successfully", null));
    }
}
