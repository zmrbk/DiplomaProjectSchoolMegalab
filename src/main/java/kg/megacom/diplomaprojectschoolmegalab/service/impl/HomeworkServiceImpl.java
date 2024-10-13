package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.HomeworkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Homework;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.HomeworkMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.HomeworkRepository; // Assume HomeworkRepository exists
import kg.megacom.diplomaprojectschoolmegalab.service.HomeworkService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления домашними заданиями.
 *
 * Этот класс предоставляет функциональность для создания, обновления, поиска и удаления
 * записей о домашних заданиях.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository; // Предполагается, что HomeworkRepository создан
    private final HomeworkMapper homeworkMapper;

    /**
     * Создание нового домашнего задания.
     *
     * @param homeworkDto объект, содержащий информацию о домашнем задании.
     */
    @Override
    public void create(HomeworkDto homeworkDto) {
        log.info("[#createHomework] is calling");
        Homework homework = homeworkMapper.toEntity(homeworkDto);
        homework.setCreationDate(LocalDateTime.now());
        homeworkRepository.save(homework);
        log.info("[#createHomework] successfully created");
    }

    /**
     * Обновление информации о домашнем задании.
     *
     * @param id идентификатор домашнего задания для обновления.
     * @param homeworkDto объект, содержащий обновленную информацию о домашнем задании.
     * @return объект Response с информацией о обновленном домашнем задании.
     * @throws RuntimeException если домашнее задание не найдено.
     */
    @Override
    public Response<HomeworkDto> update(Long id, HomeworkDto homeworkDto) {
        log.info("[#updateHomework] is calling with ID: {}", id);
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with ID: " + id));

        homework.setIsDone(homeworkDto.getIsDone());
        homework.setTeacherReview(homeworkDto.getTeacherReview());
        homework.setMark(homeworkDto.getMark());
        homework.setCreationDate(LocalDateTime.now());
        homework.setStudent(homeworkMapper.toStudent(homeworkDto.getStudentId()));
        homework.setLesson(homeworkMapper.toLesson(homeworkDto.getLessonId()));

        homeworkRepository.save(homework);
        log.info("[#updateHomework] successfully updated");
        return new Response<>("Homework updated successfully", homeworkMapper.toDto(homework));
    }

    /**
     * Получение списка всех домашних заданий.
     *
     * @return объект Response со списком всех домашних заданий.
     */
    @Override
    public Response<List<HomeworkDto>> getAll() {
        log.info("[#getAllHomeworks] is calling");
        List<HomeworkDto> homeworks = homeworkRepository.findAll().stream()
                .map(homeworkMapper::toDto)
                .collect(Collectors.toList());
        return new Response<>("All homeworks", homeworks);
    }

    /**
     * Поиск домашнего задания по идентификатору.
     *
     * @param id идентификатор домашнего задания.
     * @return объект HomeworkDto с информацией о найденном домашнем задании.
     * @throws EntityNotFoundException если домашнее задание не найдено.
     */
    @Override
    public HomeworkDto getById(Long id) {
        log.info("[#getHomeworkById] is calling with ID: {}", id);
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Homework not found with ID: " + id));
        return homeworkMapper.toDto(homework);
    }

    /**
     * Удаление домашнего задания по идентификатору.
     *
     * @param id идентификатор домашнего задания для удаления.
     * @throws RuntimeException если домашнее задание не найдено.
     */
    @Override
    public void delete(Long id) {
        log.info("[#deleteHomework] is calling with ID: {}", id);
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Homework not found with ID: " + id));
        homeworkRepository.delete(homework);
        log.info("[#deleteHomework] deleted successfully");
    }
}