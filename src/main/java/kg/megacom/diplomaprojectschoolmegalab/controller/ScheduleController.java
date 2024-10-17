package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.ScheduleDto;
import kg.megacom.diplomaprojectschoolmegalab.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Schedule", description = "APIs for managing schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Создает новое расписание.
     *
     * @param scheduleDto DTO с данными расписания.
     * @return ResponseEntity с сообщением об успешном создании расписания.
     */
    @Operation(summary = "Create a new schedule", description = "Create a new schedule in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Schedule created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Schedule Data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Response<Void>> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        log.info("[#createSchedule] is calling");
        scheduleService.create(scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>("Schedule is created successfully", null));
    }

    /**
     * Получает список всех расписаний.
     *
     * @return ResponseEntity со списком расписаний.
     */
    @Operation(summary = "Get all schedules", description = "Retrieve a list of all schedules")
    @ApiResponse(responseCode = "200", description = "List of schedules retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER', 'STUDENTS', 'PARENTS')")
    @GetMapping
    public ResponseEntity<Response<List<ScheduleDto>>> getAllSchedules() {
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
    @Operation(summary = "Get schedule by ID", description = "Retrieve a schedule by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Schedule not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'HEAD_TEACHER', 'SUBJECT_TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<ScheduleDto>> getScheduleById(@PathVariable Long id) {
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
    @Operation(summary = "Update schedule by ID", description = "Update an existing schedule by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule updated successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid Schedule Data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Schedule not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<ScheduleDto>> updateSchedule(@PathVariable Long id,
                                                                @RequestBody ScheduleDto scheduleDto) {
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
    @Operation(summary = "Delete schedule by ID", description = "Remove a schedule by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Schedule not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteSchedule(@PathVariable Long id) {
        log.info("[#deleteSchedule] is calling with ID: {}", id);
        scheduleService.delete(id);
        return ResponseEntity.ok(new Response<>("Schedule deleted successfully!", null));
    }
}
