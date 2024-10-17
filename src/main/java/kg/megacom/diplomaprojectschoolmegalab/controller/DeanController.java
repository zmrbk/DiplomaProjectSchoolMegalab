package kg.megacom.diplomaprojectschoolmegalab.controller;

import jakarta.servlet.http.HttpServletResponse;
import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.service.*;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.LessonServiceImpl;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dean")  // Завуч = Dean
@RequiredArgsConstructor
public class DeanController {

    private final ScheduleService scheduleService;
    private final SubjectService subjectService;
    private final StudentClassService studentClassService;
    private final StudentService studentService;
    private final MarkService markService;
    private final AttendanceService attendanceService;
    private final CharterService charterService;
    private final LessonServiceImpl lessonService;
    private final ReportService reportService;

    // 1. Добавление, удаление, редактирование и просмотр классов
    @PostMapping("/classes")
    public ResponseEntity<StudentClassDto> createClass(@RequestBody StudentClassDto studentClassDto) {
        StudentClassDto newClass = studentClassService.create(studentClassDto);
        return ResponseEntity.ok(newClass);
    }

    @PutMapping("/classes/{id}")
    public ResponseEntity<StudentClassDto> updateClass(@PathVariable Long id, @RequestBody StudentClassDto studentClassDto) {
        StudentClassDto updatedClass = studentClassService.update(studentClassDto, id).getData();
        return ResponseEntity.ok(updatedClass);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        studentClassService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/classes")
    public ResponseEntity<List<StudentClassDto>> getAllClasses() {
        List<StudentClassDto> classes = studentClassService.getAll().getData();
        return ResponseEntity.ok(classes);
    }

    // 2. Добавление, удаление, редактирование и просмотр предметов
    @PostMapping("/subjects")
    public ResponseEntity<SubjectsDto> createSubject(@RequestBody SubjectsDto subjectDto) {
        try {
            SubjectsDto newSubject = subjectService.create(subjectDto);
            return ResponseEntity.ok(newSubject);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<SubjectsDto> updateSubject(@PathVariable Long id, @RequestBody SubjectsDto subjectDto) {
        try {
            SubjectsDto updatedSubject = subjectService.update(subjectDto, id).getData();
            return ResponseEntity.ok(updatedSubject);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectsDto>> getAllSubjects() {
        List<SubjectsDto> subjects = subjectService.getAll().getData();
        return ResponseEntity.ok(subjects);
    }

    // 3. Просмотр, редактирование и исключение школьника из класса
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> students = studentService.getAll().getData();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        StudentDto updatedStudent = studentService.update( studentDto, id).getData();
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> removeStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Формирование отчета по успеваемости и посещаемости
    @GetMapping("/reports")
    public void getReport(HttpServletResponse response) throws IOException {
        List<String> headers = Arrays.asList("ID", "Attended", "StudentId", "LessonId");
        List<AttendanceDto> attendanceDtos = attendanceService.getAll().getData();
        List<List<Object>> data = attendanceDtos.stream()
                .map(attendance -> Arrays.asList(
                        attendance.getId().toString(),
                        attendance.getAttended(),
                        studentService.findById(attendance.getStudentId()).getData().toString(),
                        (Object) lessonService.getById(attendance.getLessonId()).toString()
                ))
                .collect(Collectors.toList());
        reportService.generateReport(response, headers, data);
    }
    // 5. Издание указов для классов
    @PostMapping("/charters")
    public ResponseEntity<CharterDto> createCharter(@RequestBody CharterDto charterDto) {
        try {
            CharterDto newCharter = charterService.create(charterDto);
            return ResponseEntity.ok(newCharter);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}