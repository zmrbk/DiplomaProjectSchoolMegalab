package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.AttendanceDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Создает новую запись о посещаемости.
     *
     * @param attendanceDto объект передачи данных, содержащий детали посещаемости
     * @return ResponseEntity с сообщением об успехе
     */
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
    @GetMapping("/{id}")
    public ResponseEntity<Response<AttendanceDto>> getAttendanceById(@PathVariable Long id) {
        log.info("[#getAttendanceById] is calling with ID: {}", id);
        AttendanceDto attendanceDto = attendanceService.getById(id);
        return ResponseEntity.ok(new Response<>("Посещение найдено", attendanceDto));
    }

    /**
     * Обновляет существующую запись о посещаемости.
     *
     * @param id идентификатор записи о посещаемости для обновления
     * @param attendanceDto обновленные данные о посещаемости
     * @return ResponseEntity с обновленной записью о посещаемости
     */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAttendance(@PathVariable Long id) {
        log.info("[#deleteAttendance] is calling with ID: {}", id);
        attendanceService.delete(id);
        return ResponseEntity.ok(new Response<>("Attendance deleted successfully!", null));
    }
}