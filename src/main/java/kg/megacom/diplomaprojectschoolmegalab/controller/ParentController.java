package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.*;
import kg.megacom.diplomaprojectschoolmegalab.service.*;
import kg.megacom.diplomaprojectschoolmegalab.service.impl.ParentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Контроллер для управления родителями.
 * Предоставляет RESTful API для создания, получения, обновления и удаления родителей.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/parents")
@Slf4j
public class ParentController {

    private final ParentServiceImpl parentService;
    private final StudentService studentService;
    private final ScheduleService scheduleService;
    private final HomeworkService homeworkService;
    private final MarkService markService;
    private final ReviewService reviewService;


    /**
     * Создает нового родителя.
     *
     * @param parentDto DTO с данными родителя.
     * @return ResponseEntity с информацией о созданном родителе.
     */
    @PostMapping
    public ResponseEntity<Response<ParentDto>> create(@RequestBody ParentDto parentDto) {
        log.info("[#createParent] is calling");
        try {
            parentService.create(parentDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>("Parent is created successfully", parentDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new Response<>("Incorrect input", null));
        }
    }

    /**
     * Получает родителя по его ID.
     *
     * @param id ID родителя.
     * @return ResponseEntity с данными родителя.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> getById(@PathVariable Long id) {
        log.info("[#getParentById] is calling");
        Response<ParentDto> response = parentService.getParentDtoById(id); // Вызов нового метода
        return ResponseEntity.ok(response);
    }

    /**
     * Получает список всех родителей.
     *
     * @return ResponseEntity со списком родителей.
     */
    @GetMapping
    public ResponseEntity<Response<List<ParentDto>>> getAll() {
        log.info("[#getAll] is calling");
        Response<List<ParentDto>> response = parentService.getAll();
        return ResponseEntity.ok(response);
    }

    /**
     * Обновляет информацию о родителе по его ID.
     *
     * @param parentDto DTO с новыми данными родителя.
     * @param id ID родителя, которого нужно обновить.
     * @return ResponseEntity с обновленной информацией о родителе.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ParentDto>> update(@RequestBody ParentDto parentDto, @PathVariable Long id) {
        log.info("[#updateParent] is calling");
        Response<ParentDto> response = parentService.update(parentDto, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Удаляет родителя по его ID.
     *
     * @param id ID родителя, которого нужно удалить.
     * @return ResponseEntity с сообщением об успешном удалении.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
        log.info("[#delete] is calling");
        parentService.delete(id);
        return ResponseEntity.ok(new Response<>("Parent deleted successfully!", "ID: " + id));
    }

    /**
     * 1) Регистрация ребенка на обучение в Школе
     *
     * @param studentDto DTO с данными ребенка.
     * @return ResponseEntity с сообщением об успешной регистрации ребенка.
     */
    @PostMapping("/register-child")
    public ResponseEntity<Response<StudentDto>> registerChild(@RequestBody StudentDto studentDto) {
        log.info("[#registerChild] is calling with data: {}", studentDto);
        try {
            Response<StudentDto> response = studentService.create(studentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("An error occurred while registering the child: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("An error occurred while registering the child", null));
        }
    }

    /**
     * 2) Получение расписания уроков ребенка
     *
     * @param studentId ID ребенка.
     * @return ResponseEntity с расписанием ребенка.
     */
    @GetMapping("/child-schedule")
    public ResponseEntity<List<ScheduleDto>> getChildSchedule(@RequestParam Long studentId) {
        log.info("[#getChildSchedule] is calling for student ID: {}", studentId);
        List<ScheduleDto> schedule = scheduleService.getScheduleByStudentId(studentId);
        return ResponseEntity.ok(schedule);
    }

    /**
     * 3) Получение списка заданий по предмету для ребенка
     *
     * @param subjectId ID предмета.
     * @return ResponseEntity со списком домашних заданий.
     */
    @GetMapping("/child-homework")
    public ResponseEntity<List<HomeworkDto>> getChildHomework(@RequestParam Long subjectId) {
        log.info("[#getChildHomework] is calling for subject ID: {}", subjectId);
        List<HomeworkDto> homeworkList = homeworkService.getHomeworkBySubjectId(subjectId);
        return ResponseEntity.ok(homeworkList);
    }

    /**
     * 4) Просмотр успеваемости ребенка
     *
     * @param studentId ID ребенка.
     * @return ResponseEntity с оценками ребенка.
     */
    @GetMapping("/child-marks")
    public ResponseEntity<List<MarkDto>> getChildMarks(@RequestParam Long studentId) {
        log.info("[#getChildMarks] is calling for student ID: {}", studentId);
        List<MarkDto> marks = markService.getMarksByStudentId(studentId);
        return ResponseEntity.ok(marks);
    }

    /**
     * 5) Просмотр отзывов от учителей
     *
     * @param studentId ID ребенка.
     * @return ResponseEntity с отзывами учителей.
     */
    @GetMapping("/child-feedback")
    public ResponseEntity<List<ReviewDto>> getTeacherFeedback(@RequestParam Long studentId) {
        log.info("[#getTeacherFeedback] is calling for student ID: {}", studentId);
        List<ReviewDto> feedbackList = reviewService.getReviewsForStudent(studentId);
        return ResponseEntity.ok(feedbackList);
    }

    /**
     * 6) Отчисление ребенка по собственному желанию
     *
     * @param studentId ID ребенка, которого нужно отчислить.
     * @return ResponseEntity с сообщением об успешном отчислении.
     */
    @DeleteMapping("/expel-child/{studentId}")
    public ResponseEntity<Response<Void>> expelChild(@PathVariable Long studentId) {
        log.info("[#expelChild] is calling for student ID: {}", studentId);
        try {
            studentService.expel(studentId);
            return ResponseEntity.ok(new Response<>("Child expelled successfully", null));
        } catch (Exception e) {
            log.error("An error occurred while expelling the child: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("An error occurred while expelling the child", null));
        }
    }
}
