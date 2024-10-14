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
/**
 * Mapper для преобразования между сущностью {@link Assignment} и DTO {@link AssignmentDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Assignment} в {@link AssignmentDto}
 * и наоборот, а также для получения авторов и получателей из репозиториев.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AssignmentMapper {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    /**
     * Преобразует сущность {@link Assignment} в {@link AssignmentDto}.
     *
     * @param assignment Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link AssignmentDto}.
     */
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

    /**
     * Преобразует объект {@link AssignmentDto} в сущность {@link Assignment}.
     *
     * @param assignmentDto DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Assignment}.
     */
    public Assignment toEntity(AssignmentDto assignmentDto) {
        Assignment assignment = new Assignment();
        assignment.setId(assignmentDto.getId());
        assignment.setAssignment(assignmentDto.getAssignment());
        assignment.setIsDone(assignmentDto.getIsDone());
        assignment.setAuthor(toAuthor(assignmentDto.getAuthorId()));
        assignment.setReceiver(toReceiver(assignmentDto.getReceiverId()));
        return assignment;
    }

    /**
     * Получает автора по идентификатору.
     *
     * @param authorId Идентификатор автора.
     * @return Сущность {@link Employee} автора.
     * @throws EntityNotFoundException Если автор не найден.
     */
    public Employee toAuthor(Long authorId) {
        return employeeRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with ID: " + authorId));
    }

    /**
     * Получает получателя по идентификатору.
     *
     * @param receiverId Идентификатор получателя.
     * @return Сущность {@link User} получателя.
     * @throws EntityNotFoundException Если получатель не найден.
     */
    public User toReceiver(Long receiverId) {
        return userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found with ID: " + receiverId));
    }
}