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

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping(value = "/create")
    public ResponseEntity<Response<Void>> createAttendance(@RequestBody AttendanceDto attendanceDto) {
        log.info("[#createAttendance] is calling");
        attendanceService.create(attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Attendance created successfully", null));
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<Response<List<AttendanceDto>>> getAllAttendances() {
        log.info("[#getAllAttendances] is calling");
        Response<List<AttendanceDto>> response = attendanceService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AttendanceDto>> getAttendanceById(@PathVariable Long id) {
        log.info("[#getAttendanceById] is calling with ID: {}", id);
        AttendanceDto attendanceDto = attendanceService.getById(id);
        return ResponseEntity.ok(new Response<>("Attendance found", attendanceDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<AttendanceDto>> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDto attendanceDto) {
        log.info("[#updateAttendance] is calling with ID: {}", id);
        Response<AttendanceDto> response = attendanceService.update(id, attendanceDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteAttendance(@PathVariable Long id) {
        log.info("[#deleteAttendance] is calling with ID: {}", id);
        attendanceService.delete(id);
        return ResponseEntity.ok(new Response<>("Attendance deleted successfully", null));
    }
}
