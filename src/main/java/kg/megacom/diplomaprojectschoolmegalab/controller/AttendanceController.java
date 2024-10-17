package kg.megacom.diplomaprojectschoolmegalab.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.megacom.diplomaprojectschoolmegalab.dto.AttendanceDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * AttendanceController - это REST-контроллер, который управляет посещаемостью
 * в системе управления школой. Он предоставляет конечные точки для создания,
 * получения, обновления и удаления записей о посещаемости.
 *
 * <p>Контроллер использует {@link AttendanceService} для выполнения
 * бизнес-логики, связанной с посещаемостью.</p>
 */
@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Attendance", description = "APIs for managing attendance records")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Создает новую запись о посещаемости.
     *
     * @param attendanceDto объект передачи данных, содержащий детали посещаемости
     * @return ResponseEntity с сообщением об успехе
     */
    @Operation(summary = "Create a new attendance record", description = "Create a new attendance record with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Attendance created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PreAuthorize("hasAnyRole('CLASS_TEACHER')")
    @PostMapping(value = "/create")
    public ResponseEntity<Response<Void>> createAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("[#createAttendance] is calling");
        attendanceService.create(attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Attendance is created successfully", null));
    }

    /**
     * Получает все записи о посещаемости.
     *
     * @return ResponseEntity со списком всех записей о посещаемости
     */
    @Operation(summary = "Get all attendance records", description = "Retrieve a list of all attendance records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attendance records retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'CLASS_TEACHER')")
    @GetMapping(value = "/get-all")
    public ResponseEntity<Response<List<AttendanceDto>>> getAllAttendances() {
        log.info("[#getAllAttendances] is calling");
        Response<List<AttendanceDto>> response = attendanceService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Получает запись о посещаемости по ее идентификатору.
     *
     * @param id идентификатор записи о посещаемости
     * @return ResponseEntity с данными о посещаемости
     */
    @Operation(summary = "Get attendance record by ID", description = "Retrieve an attendance record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attendance record found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDto.class))),
            @ApiResponse(responseCode = "404", description = "Attendance record not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'CLASS_TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<AttendanceDto>> getAttendanceById(@PathVariable Long id) {
        log.info("[#getAttendanceById] is calling with ID: {}", id);
        AttendanceDto attendanceDto = attendanceService.getById(id);
        return ResponseEntity.ok(new Response<>("Attendance record found", attendanceDto));
    }

    /**
     * Обновляет существующую запись о посещаемости.
     *
     * @param id идентификатор записи о посещаемости для обновления
     * @param attendanceDto обновленные данные о посещаемости
     * @return ResponseEntity с обновленной записью о посещаемости
     */
    @Operation(summary = "Update an attendance record", description = "Update an existing attendance record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attendance updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AttendanceDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict in updating the attendance record", content = @Content)
    })
    @PreAuthorize("hasAnyRole('DIRECTOR', 'CLASS_TEACHER')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<AttendanceDto>> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDto attendanceDto) {
        log.info("[#updateAttendance] is calling with ID: {}", id);
        Response<AttendanceDto> response = attendanceService.update(id, attendanceDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет запись о посещаемости по ее идентификатору.
     *
     * @param id идентификатор записи о посещаемости для удаления
     * @return ResponseEntity с сообщением об успехе
     */
    @Operation(summary = "Delete an attendance record", description = "Delete an attendance record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attendance record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Attendance record not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('CLASS_TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAttendance(@PathVariable Long id) {
        log.info("[#deleteAttendance] is calling with ID: {}", id);
        attendanceService.delete(id);
        return ResponseEntity.ok(new Response<>("Attendance record deleted successfully!", null));
    }
}
