package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.ReviewDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/homeroom")
@RequiredArgsConstructor
public class HomeroomTeacherController {

    private final AssignmentService assignmentService;
    private final StudentService studentService;
    private final ReviewService reviewService;

    // 1. Просмотр поручений от завуча
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsFromDean() {
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleName("DEAN");
        roles.add(role);

        List<AssignmentDto> assignments = assignmentService.getAssignmentsByAuthorRole(roles);
        return ResponseEntity.ok(assignments);
    }

    // 2. Исполнение поручений от завуча
    @PostMapping("/assignments/{id}/complete")
    public ResponseEntity<AssignmentDto> completeAssignment(@PathVariable Long id) {
        AssignmentDto completedAssignment = assignmentService.markAsCompleted(id);
        return ResponseEntity.ok(completedAssignment);
    }

    // 3. Назначение классного старосты
    @PostMapping("/students/{id}/appoint-captain")
    public ResponseEntity<StudentDto> appointClassCaptain(@PathVariable Long id) {
        StudentDto captain = studentService.appointClassCaptain(id);
        return ResponseEntity.ok(captain);
    }

    // 4. Просмотр списка учеников в классе
    @GetMapping("/students/{classId}")
    public ResponseEntity<List<StudentDto>> getStudentsInClass(Long classId) {
        List<StudentDto> students = studentService.getAllStudentsInClass(classId);
        return ResponseEntity.ok(students);
    }

    // 5. Просмотр отзывов об ученике от других преподавателей
    @GetMapping("/students/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getStudentReviews(@PathVariable Long id) {
        List<ReviewDto> reviews = reviewService.getReviewsForStudent(id);
        return ResponseEntity.ok(reviews);
    }

    // 6. Формирование автобиографии ученика
    @PostMapping("/students/{id}/autobiography")
    public ResponseEntity<CharterDto> createStudentAutobiography(@PathVariable Long id, @RequestBody CharterDto charterDto) {
        CharterDto autobiography = studentService.createAutobiography(id, charterDto);
        return ResponseEntity.ok(autobiography);
    }

    // 7. Создание поручений для старосты
    @PostMapping("/assignments/captain")
    public ResponseEntity<AssignmentDto> createAssignmentForCaptain(@RequestBody AssignmentDto assignmentDto) {
        AssignmentDto assignment = assignmentService.createAssignmentForCaptain(assignmentDto);
        return ResponseEntity.ok(assignment);
    }

    // 8. Просмотр списка выполненных поручений от старосты
    @GetMapping("/assignments/captain/completed/{captainId}")
    public ResponseEntity<List<AssignmentDto>> getCompletedAssignmentsByCaptain(@PathVariable Long captainId) {
        List<AssignmentDto> assignments = assignmentService.getCompletedAssignmentsByCaptain(captainId);
        return ResponseEntity.ok(assignments);
    }

    // 9. Просмотр отзывов об учениках, сформированных старостой
    @GetMapping("/students/{id}/captain-reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByCaptain(@PathVariable Long id) {
        List<ReviewDto> reviews = reviewService.getReviewsByCaptain(id);
        return ResponseEntity.ok(reviews);
    }
}