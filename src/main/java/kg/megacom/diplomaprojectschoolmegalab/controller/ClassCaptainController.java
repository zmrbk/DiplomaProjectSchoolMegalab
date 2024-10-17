package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.DutyScheduleDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.service.AssignmentService;
import kg.megacom.diplomaprojectschoolmegalab.service.DutyScheduleService;
import kg.megacom.diplomaprojectschoolmegalab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/captain")
@RequiredArgsConstructor
public class ClassCaptainController {

    private final AssignmentService assignmentService;
    private final ReviewService reviewService;
    private final DutyScheduleService dutyScheduleService;

    // 1. Составление отзыва об однокласснике
    @PostMapping("/students/{studentId}/review")
    public ResponseEntity<ReviewDto> createReviewForStudent(@PathVariable Long studentId, @RequestBody ReviewDto reviewDto) {
        reviewDto.setStudentId(studentId);
        try {
            ReviewDto createdReview = reviewService.create(reviewDto);
            return ResponseEntity.ok(createdReview);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. Выполнение поручений от классного руководителя
    @PostMapping("/assignments/{assignmentId}/complete")
    public ResponseEntity<Void> completeAssignment(@PathVariable Long assignmentId) {
        assignmentService.completeAssignment(assignmentId);
        return ResponseEntity.ok().build();
    }

    // 3. Просмотр списка поручений от классного руководителя
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDto>> getAssignments() {
        List<AssignmentDto> assignments = assignmentService.getAssignmentsByCaptainRole();

        return ResponseEntity.ok(assignments);
    }

    // 4. Составление графика дежурств по классу
    //[
    //    "Ivan Ivanov",
    //    "Petr Petrov",
    //    "Sergey Sergeev"
    //]
    @PostMapping("/duty-schedule")
    public ResponseEntity<DutyScheduleDto> createDutySchedule(@RequestBody List<String> dutyList) {
        // Вызов сервиса для создания графика дежурств
        DutyScheduleDto schedule = dutyScheduleService.createDutySchedule(dutyList);
        return ResponseEntity.ok(schedule);
    }
}