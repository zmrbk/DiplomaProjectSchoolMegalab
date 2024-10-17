package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final ScheduleService scheduleService;
    private final StudentClassService studentClassService;
    private final StudentService studentService;
    private final MarkService markService;
    private final AttendanceService attendanceService;
    private final HomeworkService homeworkService;
    private final ReviewService reviewService;

    // 1. Просмотр расписания уроков преподавателя
    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleDto>> getTeacherSchedule(@RequestParam Long teacherId) {
        List<ScheduleDto> schedule = scheduleService.getScheduleByTeacherId(teacherId);
        return ResponseEntity.ok(schedule);
    }

    // 2. Просмотр списка классов, где преподает учитель
    @GetMapping("/classes")
    public ResponseEntity<List<StudentClassDto>> getClassesByTeacher(@RequestParam Long teacherId) {
        List<StudentClassDto> classes = studentClassService.getClassesByTeacherId(teacherId);
        return ResponseEntity.ok(classes);
    }

    // 3. Просмотр списка учеников в классе
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudentsInClass(@RequestParam Long classId) {
        List<StudentDto> students = studentService.getAllStudentsInClass(classId);
        return ResponseEntity.ok(students);
    }

    // 4. Просмотр успеваемости учеников по предмету
    @GetMapping("/marks")
    public ResponseEntity<List<MarkDto>> getMarksByClassAndSubject(@RequestParam Long classId, @RequestParam Long subjectId) {
        List<MarkDto> marks = markService.getMarksByClassAndSubject(classId, subjectId);
        return ResponseEntity.ok(marks);
    }

    // 5. Составление отзыва о студенте
    @PostMapping("/students/review")
    public ResponseEntity<ReviewDto> createReviewForStudent( @RequestBody ReviewDto reviewDto) {
        try {
            ReviewDto createdReview = reviewService.create(reviewDto);
            return ResponseEntity.ok(createdReview);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 6. Формирование домашнего задания
    @PostMapping("/homework")
    public ResponseEntity<HomeworkDto> createHomework(@RequestBody HomeworkDto homeworkDto) {
        HomeworkDto createdHomework = homeworkService.create(homeworkDto);
        return ResponseEntity.ok(createdHomework);
    }

    // 7. Просмотр списка выполненных домашних заданий
    @GetMapping("/homework/completed")
    public ResponseEntity<List<HomeworkDto>> getCompletedHomework(@RequestParam Long classId, @RequestParam Long subjectId) {
        List<HomeworkDto> completedHomework = homeworkService.getCompletedHomework(classId, subjectId);
        return ResponseEntity.ok(completedHomework);
    }

    // 8. Оценка выполненных домашних заданий
    @PostMapping("/homework/{homeworkId}/grade")
    public ResponseEntity<MarkDto> gradeHomework(@PathVariable Long homeworkId, @RequestBody MarkDto markDto) {
        MarkDto gradedHomework = homeworkService.gradeHomework(homeworkId, markDto);
        return ResponseEntity.ok(gradedHomework);
    }
}