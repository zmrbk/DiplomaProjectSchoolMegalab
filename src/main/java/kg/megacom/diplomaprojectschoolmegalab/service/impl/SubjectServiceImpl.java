package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.TopicDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Subject;
import kg.megacom.diplomaprojectschoolmegalab.entity.Topic;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityAlreadyExistsException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.SubjectMapper;
import kg.megacom.diplomaprojectschoolmegalab.mappers.TopicMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.SubjectRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с предметами.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения предметов.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final TopicMapper topicMapper;

    /**
     * Создание нового предмета.
     *
     * @param subjectsDto объект предмета, который нужно создать.
     * @return
     * @throws EntityAlreadyExistsException если предмет с таким названием уже существует.
     */
    @Override
    public SubjectsDto create(SubjectsDto subjectsDto) throws EntityNotFoundException {
        if (subjectRepository.existsByTitle(subjectsDto.getTitle())) {
            throw new EntityAlreadyExistsException("A subject with this title already exists");
        }
        Subject subject = subjectMapper.toSubject(subjectsDto);
        log.info("Create subject with title: {}", subjectsDto.getTitle());
        subjectRepository.save(subject);
        return subjectMapper.toSubjectDto(subject);
    }

    /**
     * Обновление существующего предмета.
     *
     * @param subjectsDto объект предмета с обновленной информацией.
     * @param id идентификатор предмета, который нужно обновить.
     * @return ответ с информацией об обновленном предмете.
     * @throws EntityNotFoundException если предмет с указанным идентификатором не найден.
     */
    @Override
    public Response<SubjectsDto> update(SubjectsDto subjectsDto, Long id) throws EntityNotFoundException {
        if (subjectRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Subject not found");
        }
        Subject subject = subjectMapper.toSubject(subjectsDto);
        subject.setId(id);
        subjectRepository.save(subject);
        return new Response<>("Subject updated successfully", subjectMapper.toSubjectDto(subject));
    }

    /**
     * Удаление предмета.
     *
     * @param id идентификатор предмета, который нужно удалить.
     * @return ответ с информацией о результате операции удаления.
     * @throws EntityNotFoundException если предмет с указанным идентификатором не найден.
     */
    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {
        if (subjectRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Subject not found");
        }
        subjectRepository.deleteById(id);
        return new Response<>("Subject: " + id, "Deleted successfully");
    }

    /**
     * Получение всех предметов.
     *
     * @return ответ со списком всех предметов.
     */
    @Override
    public Response<List<SubjectsDto>> getAll() {
        List<SubjectsDto> list = subjectMapper.toSubjectsDtoList(subjectRepository.findAll());
        return new Response<>("All subjects retrieved successfully", list);
    }

    /**
     * Получение предмета по идентификатору.
     *
     * @param id идентификатор предмета, который нужно получить.
     * @return ответ с найденным предметом.
     * @throws EntityNotFoundException если предмет с указанным идентификатором не найден.
     */
    @Override
    public Response<SubjectsDto> getById(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Subject not found"));
        return new Response<>("Get Subject ID", subjectMapper.toSubjectDto(subject));
    }

    @Override
    public List<SubjectsDto> getSubjectsByStudentId(Long studentId) {
        List<Subject> subjects = subjectRepository.findSubjectsByStudentId(studentId);
        if (subjects.isEmpty()) {
            throw new EntityNotFoundException("No subjects found for student ID: " + studentId);
        }
        return subjectMapper.toSubjectsDtoList(subjects);  // Assuming you have a proper mapper here
    }

    @Override
    public List<TopicDto> getTopicsBySubjectId(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));

        // Retrieve topics from the subject
        List<Topic> topics = subject.getTopics();

        if (topics.isEmpty()) {
            throw new EntityNotFoundException("No topics found for subject ID: " + subjectId);
        }

        // Convert Topic entities to TopicDto and return
        return topics.stream()
                .map(topicMapper::toTopicDto)
                .collect(Collectors.toList());
    }
}