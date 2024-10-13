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

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Response<Void>> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        log.info("[#createSchedule] is calling");
        scheduleService.create(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("Schedule created successfully", null));
    }

    @GetMapping
    public ResponseEntity<Response<List<ScheduleDto>>> getAllSchedules() {
        log.info("[#getAllSchedules] is calling");
        Response<List<ScheduleDto>> response = scheduleService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ScheduleDto>> getScheduleById(@PathVariable Long id) {
        log.info("[#getScheduleById] is calling with ID: {}", id);
        ScheduleDto scheduleDto = scheduleService.getById(id);
        return ResponseEntity.ok(new Response<>("Schedule found", scheduleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ScheduleDto>> updateSchedule(@PathVariable Long id,
                                                                @RequestBody ScheduleDto scheduleDto) {
        log.info("[#updateSchedule] is calling with ID: {}", id);
        Response<ScheduleDto> response = scheduleService.update(id, scheduleDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteSchedule(@PathVariable Long id) {
        log.info("[#deleteSchedule] is calling with ID: {}", id);
        scheduleService.delete(id);
        return ResponseEntity.ok(new Response<>("Schedule deleted successfully", null));
    }
}

