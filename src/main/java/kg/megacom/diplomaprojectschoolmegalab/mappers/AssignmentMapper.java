package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Assignment;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.UserRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AssignmentMapper {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    // Convert Assignment entity to AssignmentDto
    public AssignmentDto toDto(Assignment assignment) {
        AssignmentDto assignmentDto = new AssignmentDto();
        assignmentDto.setId(assignment.getId());
        assignmentDto.setAssignment(assignment.getAssignment());
        assignmentDto.setCreationDate(assignment.getCreationDate());
        assignmentDto.setIsDone(assignment.getIsDone());
        assignmentDto.setAuthorId(assignment.getAuthor().getId());
        assignmentDto.setReceiverId(assignment.getReceiver().getId());
        return assignmentDto;
    }

    // Convert AssignmentDto to Assignment entity
    public Assignment toEntity(AssignmentDto assignmentDto) {
        Assignment assignment = new Assignment();
        assignment.setId(assignmentDto.getId());
        assignment.setAssignment(assignmentDto.getAssignment());
        assignment.setIsDone(assignmentDto.getIsDone());
        assignment.setAuthor(toAuthor(assignmentDto.getAuthorId()));
        assignment.setReceiver(toReceiver(assignmentDto.getReceiverId()));
        return assignment;
    }

    public Employee toAuthor(Long authorId) {
        return employeeRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + authorId));
    }

    public User toReceiver(Long receiverId) {
        return userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found with ID: " + receiverId));
    }
}
