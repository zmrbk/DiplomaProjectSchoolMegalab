package kg.megacom.diplomaprojectschoolmegalab.controller;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import kg.megacom.diplomaprojectschoolmegalab.repository.CharterRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.AssignmentService;
import kg.megacom.diplomaprojectschoolmegalab.service.CharterService;
import kg.megacom.diplomaprojectschoolmegalab.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/secretary")
@RequiredArgsConstructor
public class SecretaryController {

    private final AssignmentService assignmentService;
    private final CharterService charterService;
    private final ParentService parentService;
    private final CharterRepository charterRepository;

    // 1. Просмотр поручений от директора
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDto>> getDirectorAssignments() {
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleName("DIRECTOR");
        roles.add(role);
        List<AssignmentDto> assignments = assignmentService.getAssignmentsByAuthorRole(roles);
        return ResponseEntity.ok(assignments);
    }

    // 2. Исполнение поручений директора (отметить выполнение поручения)
    @PostMapping("/assignments/{id}/complete")
    public ResponseEntity<AssignmentDto> completeAssignment(@PathVariable Long id) {
        AssignmentDto completedAssignment = assignmentService.markAsCompleted(id);
        return ResponseEntity.ok(completedAssignment);
    }

    // 3. Просмотр списка заявок на поступление новых детей в школу
    @GetMapping("/parents/applications")
    public ResponseEntity<List<ParentDto>> getParentApplications() {
        List<ParentDto> applications = parentService.getPendingApplications();
        return ResponseEntity.ok(applications);
    }

    // 4. Ведение делопроизводства (просмотр всех уставов)
    @GetMapping("/charters")
    public ResponseEntity<List<CharterDto>> getAllCharters() {
        List<CharterDto> charters = charterService.getAll().getData();
        return ResponseEntity.ok(charters);
    }

    // 5. Добавление нового устава
    @PostMapping("/charters")
    public ResponseEntity<CharterDto> createCharter(@RequestBody CharterDto charterDto) {
        CharterDto newChart;
        try {
            newChart = charterService.create(charterDto);
            return ResponseEntity.ok(newChart);

        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}