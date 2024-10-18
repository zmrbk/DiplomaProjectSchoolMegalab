package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления студентами.
 * Предоставляет RESTful API для создания, получения, обновления и удаления студентов.
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final SubjectService subjectService;
    private final StudentService studentService;
    private final LessonService lessonService;
    private final HomeworkService homeworkService;
    private final MarkService markService;
    private final ScheduleService scheduleService;

    /**
     * Создает нового студента.
     *
     * @param studentDto DTO с данными студента.
     * @return ResponseEntity с сообщением об успешном создании студента.
     */
    @PostMapping
    public ResponseEntity<Response<StudentDto>> create(@RequestBody StudentDto studentDto) {
        log.info("[#createStudent] is calling with data: {}", studentDto);
        try {
            Response<StudentDto> response = studentService.create(studentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            log.error("An error occurred while creating the student: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while creating the student", null));
        }
    }

    /**
     * Получает студента по его ID.
     *
     * @param id ID студента, которого нужно получить.
     * @return ResponseEntity с найденным студентом.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<StudentDto>> findById(@PathVariable Long id) {
        log.info("[#getStudentById] is calling");
        try {
            Response<StudentDto> response = studentService.findById(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while retrieving the student", null));
        }
    }

    /**
     * Получает список всех студентов.
     *
     * @return ResponseEntity со списком студентов.
     */
    @GetMapping
    public ResponseEntity<Response<List<StudentDto>>> getAll() {
        log.info("[#getAllStudents] is calling");
        try {
            Response<List<StudentDto>> response = studentService.getAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while retrieving students", null));
        }
    }

    /**
     * Обновляет информацию о студенте.
     *
     * @param studentDto DTO с новыми данными студента.
     * @return ResponseEntity с обновленным студентом.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<StudentDto>> update(@RequestBody StudentDto studentDto, Long id) {
        log.info("[#updateStudent] is calling");
        try {
            Response<StudentDto> response = studentService.update(studentDto, id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while updating the student", null));
        }
    }

    /**
     * Удаляет студента по его ID.
     *
     * @param id ID студента, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении студента.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable Long id) {
        log.info("[#deleteStudent] is calling");
        try {
            Response<Void> response = studentService.delete(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("An error occurred while deleting the student", null));
        }
    }

    // 1. Просмотр списка своих предметов
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectsDto>> getStudentSubjects(@RequestParam Long studentId) {
        List<SubjectsDto> subjects = subjectService.getSubjectsByStudentId(studentId);
        return ResponseEntity.ok(subjects);
    }

    // 2. Просмотр списка учеников в классе
    @GetMapping("/classmates")
    public ResponseEntity<List<StudentDto>> getAllStudentsInClass(@RequestParam Long classId) {
        List<StudentDto> classmates = studentService.getAllStudentsInClass(classId);
        return ResponseEntity.ok(classmates);
    }

    // 3. Просмотр списка тем по предмету
    @GetMapping("/subjects/{subjectId}/topics")
    public ResponseEntity<List<TopicDto>> getSubjectTopics(@PathVariable Long subjectId) {
        List<TopicDto> topics = subjectService.getTopicsBySubjectId(subjectId);
        return ResponseEntity.ok(topics);
    }

    // 4. Просмотр списка уроков
    @GetMapping("/lessons")
    public ResponseEntity<List<LessonDto>> getLessonsBySubject(@RequestParam Long subjectId) {
        List<LessonDto> lessons = lessonService.getLessonsBySubjectId(subjectId);
        return ResponseEntity.ok(lessons);
    }

    // 5. Просмотр списка домашних работ по уроку
    @GetMapping("/homework")
    public ResponseEntity<List<HomeworkDto>> getHomeworkByLesson(@RequestParam Long lessonId) {
        List<HomeworkDto> homeworkList = homeworkService.getHomeworkByLessonId(lessonId);
        return ResponseEntity.ok(homeworkList);
    }

    // 6. Отправка выполненой домашней работы
    @PostMapping("/homework/submit")
    public ResponseEntity<HomeworkDto> submitHomework(@RequestBody HomeworkSubmissionDto submissionDto) {
        HomeworkDto submittedHomework = homeworkService.submitHomework(submissionDto);
        return ResponseEntity.ok(submittedHomework);
    }

    // 7. Просмотр списка оценок
    @GetMapping("/marks")
    public ResponseEntity<List<MarkDto>> getMarksByStudent(@RequestParam Long studentId) {
        List<MarkDto> marks = markService.getMarksByStudentId(studentId);
        return ResponseEntity.ok(marks);
    }

    // 8. Получение расписания
    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleDto>> getStudentSchedule(@RequestParam Long studentId) {
        List<ScheduleDto> schedule = scheduleService.getScheduleByStudentId(studentId);
        return ResponseEntity.ok(schedule);
    }
}
