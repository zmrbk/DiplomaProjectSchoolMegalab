package kg.megacom.diplomaprojectschoolmegalab.controller;

import jakarta.servlet.http.HttpServletResponse;
import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.PasswordResetService;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/director")
@Slf4j
public class DirectorController {
    private final UserServiceImpl userService;
    private final EmployeeServiceImpl employeeService;
    private final SubjectServiceImpl subjectService; // предметы
    private final ScheduleServiceImpl scheduleService; // расписание
    private final AttendanceServiceImpl attendanceService; // посещаемость
    private final MarkServiceImpl markService; // оценки
    private final ReportService reportService;
    private final StudentServiceImpl studentService;

    private final AnnouncementServiceImpl announcementService; // обьявление
    private final LessonServiceImpl lessonService;

    private final MessageServiceImpl messageService;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////// Добавление, редактирование, просмотр списка преподавателей, удаление работников/////////////////////

    @PostMapping(value = "/create-employee")
    public ResponseEntity<Response<EmployeeDto>> create(@RequestBody EmployeeDto employeeCreateRequest) {
        log.info("[#createEmployee] is calling");
        try {
            employeeService.create(employeeCreateRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Employee is created successfully", employeeCreateRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    @GetMapping(value = "/get-employee/{id}")
    public ResponseEntity<Response<EmployeeDto>> getById(@PathVariable Long id) {
        log.info("[#getEmployeeById] is calling");
        Response<EmployeeDto> response = employeeService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/get-all-employee")
    public ResponseEntity<Response<List<EmployeeDto>>> getAll() {
        log.info("[#getAllEmployees] is calling");
        Response<List<EmployeeDto>> response = employeeService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update-employee/{id}")
    public ResponseEntity<Response<EmployeeDto>> update(@RequestBody EmployeeDto employeeCreateRequest, @PathVariable Long id) {
        log.info("[#updateEmployee] is calling");
        Response<EmployeeDto> response = employeeService.update(employeeCreateRequest, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete-employee/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        employeeService.delete(id);
        return ResponseEntity.ok(new Response<>("Employee deleted successfully!", "ID: " + id));
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////// Добавление, редактирование Предметов///////////////////////////////////////////

    @PostMapping(value = "/create-subject")
    public ResponseEntity<Response<SubjectsDto>> createSubject(@RequestBody SubjectsDto subjectsDto) {
        log.info("[#create] is calling");
        try {
            subjectService.create(subjectsDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Subject is created successfully", subjectsDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    @GetMapping(value = "/subject/{id}")
    public ResponseEntity<Response<SubjectsDto>> getSubjectById(@PathVariable Long id) {
        log.info("[#getById] is calling");
        Response<SubjectsDto> response = subjectService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/subject/{id}")
    public ResponseEntity<Response<SubjectsDto>> updateSubject(@RequestBody SubjectsDto subjectsDto, @PathVariable Long id) {
        log.info("[#update] is calling");
        Response<SubjectsDto> response = subjectService.update(subjectsDto, id);
        return ResponseEntity.ok(response);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////// Утверждение расписания ////////////////////////////////////////////

    @PutMapping("/schedule/{id}")
    public ResponseEntity<Response<ScheduleDto>> setApprove(@PathVariable Long id,
                                                                @RequestParam Boolean isApprove) {
        log.info("[#updateSchedule] is calling with ID: {}", id);
        Response<ScheduleDto> response = scheduleService.setApprove(id, isApprove);
        return ResponseEntity.ok(response);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////Просмотр успеваемости и посещаемости учеников//////////////////////////////////

    @GetMapping(value = "/get-all-attendances")
    public ResponseEntity<Response<List<AttendanceDto>>> getAllAttendances() {
        log.info("[#getAllAttendances] is calling");
        Response<List<AttendanceDto>> response = attendanceService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<MarkDto>>> getAllMark() {
        log.info("[#getAllMarks] is calling");
        Response<List<MarkDto>> response = markService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports")
    public void getReport(HttpServletResponse response) throws IOException {
        List<String> headers = Arrays.asList("ID", "Attended", "StudentId", "LessonId");
        List<AttendanceDto> attendanceDtos = attendanceService.getAll().getData();
        List<List<Object>> data = attendanceDtos.stream()
                .map(attendance -> Arrays.asList(
                         attendance.getId().toString(),
                         attendance.getAttended(),
                         studentService.findById(attendance.getStudentId()).getData().toString(),
                                (Object)lessonService.getById(attendance.getLessonId()).toString()
                ))
                .collect(Collectors.toList());
        reportService.generateReport(response, headers, data);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    ///////////Взаимодействие с родителями через систему (объявления, сообщения)///////////////

    @PostMapping(value = "/announcement")
    public ResponseEntity<Response<AnnouncementDto>> create(@RequestBody AnnouncementDto announcementDto) {
        log.info("[#createAnnouncement] is calling");
        try {
            AnnouncementDto newAnnouncement = announcementService.create(announcementDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Announcement is created successfully", newAnnouncement));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new Response<>("Incorrect input", null));
        }
    }

    @GetMapping(value = "/announcement/{id}")
    public ResponseEntity<Response<AnnouncementDto>> getAnnouncementById(@PathVariable Long id) {
        log.info("[#getGradeById] is calling");
        Response<AnnouncementDto> response = announcementService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/announcements")
    public ResponseEntity<Response<List<AnnouncementDto>>> getAllannouncement() {
        log.info("[#getAllGrades] is calling");
        Response<List<AnnouncementDto>> response = announcementService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/announcement/{id}")
    public ResponseEntity<?> updateAnnouncement(@RequestBody AnnouncementDto announcementDto, @PathVariable Long id) {
        try {
            Response<AnnouncementDto> response = announcementService.update(announcementDto, id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/announcement/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id) {
        log.info("[#delete] is calling");
        try {
            announcementService.delete(id);
            return ResponseEntity.ok(("Announcement deleted successfully!"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Response<Void>> createMessage(@RequestBody MessageDto messageDto) {
        log.info("[#createMessage] is calling");
        messageService.create(messageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Message is created successfully", null));
    }

}
