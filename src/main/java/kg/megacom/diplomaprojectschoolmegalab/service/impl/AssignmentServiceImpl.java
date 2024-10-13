package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Assignment;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.AssignmentMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.AssignmentRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Реализация сервиса для управления заданиями.
 *
 * Этот класс отвечает за операции, связанные с заданиями, такие как создание,
 * обновление, удаление и получение заданий.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    /**
     * Создает новое задание.
     *
     * @param assignmentDto объект DTO задания, содержащий данные для создания.
     */
    @Override
    public void create(AssignmentDto assignmentDto) {
        log.info("[#createAssignment] is calling");
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        assignmentRepository.save(assignment);
        log.info("[#createAssignment] successfully created");
    }

    /**
     * Обновляет существующее задание.
     *
     * @param id идентификатор задания, которое необходимо обновить.
     * @param assignmentDto объект DTO задания, содержащий обновленные данные.
     * @return объект Response с обновленным заданием.
     * @throws EntityNotFoundException если задание с указанным идентификатором не найдено.
     */
    @Override
    public Response<AssignmentDto> update(Long id, AssignmentDto assignmentDto) {
        log.info("[#updateAssignment] is calling with ID: {}", id);
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id));

        assignment.setAssignment(assignmentDto.getAssignment());
        assignment.setIsDone(assignmentDto.getIsDone());
        assignment.setAuthor(assignmentMapper.toAuthor(assignmentDto.getAuthorId()));
        assignment.setReceiver(assignmentMapper.toReceiver(assignmentDto.getReceiverId()));

        assignmentRepository.save(assignment);
        log.info("[#updateAssignment] successfully updated");
        return new Response<>("Assignment updated successfully", assignmentMapper.toDto(assignment));
    }

    /**
     * Получает все задания.
     *
     * @return объект Response, содержащий список всех заданий.
     */
    @Override
    public Response<List<AssignmentDto>> getAll() {
        log.info("[#getAllAssignments] is calling");
        List<AssignmentDto> assignments = assignmentRepository.findAll().stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All assignments", assignments);
    }

    /**
     * Получает задание по идентификатору.
     *
     * @param id идентификатор задания, которое необходимо получить.
     * @return объект DTO найденного задания.
     * @throws EntityNotFoundException если задание с указанным идентификатором не найдено.
     */
    @Override
    public AssignmentDto getById(Long id) {
        log.info("[#getAssignmentById] is calling with ID: {}", id);
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id));
        return assignmentMapper.toDto(assignment);
    }

    /**
     * Удаляет задание по идентификатору.
     *
     * @param id идентификатор задания, которое необходимо удалить.
     * @throws EntityNotFoundException если задание с указанным идентификатором не найдено.
     */
    @Override
    public void delete(Long id) {
        log.info("[#deleteAssignment] is calling with ID: {}", id);
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + id));
        assignmentRepository.delete(assignment);
        log.info("[#deleteAssignment] deleted successfully");
    }
}