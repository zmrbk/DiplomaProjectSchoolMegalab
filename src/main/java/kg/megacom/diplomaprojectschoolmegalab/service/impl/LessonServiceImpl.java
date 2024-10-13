package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.LessonDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Lesson;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.LessonMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.LessonRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.LessonService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с уроками.
 *
 * Этот класс предоставляет функциональность для создания, обновления,
 * получения и удаления уроков.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    /**
     * Создание нового урока.
     *
     * @param lessonDto объект, содержащий данные урока для создания.
     */
    @Override
    public void create(LessonDto lessonDto) {
        Lesson lesson = lessonMapper.toLesson(lessonDto);
        lessonRepository.save(lesson);
        log.info("Created lesson: {}", lesson);
    }

    /**
     * Получение списка всех уроков.
     *
     * @return список всех уроков в виде Response с сообщением и списком LessonDto.
     */
    @Override
    public Response<List<LessonDto>> getAll() {
        List<Lesson> lessons = lessonRepository.findAll();
        List<LessonDto> lessonDtoList = lessons.stream()
                .map(lessonMapper::toLessonDto)
                .collect(Collectors.toList());
        return new Response<>("Lessons retrieved successfully", lessonDtoList);
    }

    /**
     * Получение урока по его идентификатору.
     *
     * @param id идентификатор урока.
     * @return объект LessonDto с данными найденного урока.
     * @throws EntityNotFoundException если урок с указанным идентификатором не найден.
     */
    @Override
    public LessonDto getById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        return lessonMapper.toLessonDto(lesson);
    }

    /**
     * Обновление данных урока.
     *
     * @param id идентификатор урока, который нужно обновить.
     * @param lessonDto объект, содержащий обновленные данные урока.
     * @return Response с сообщением об успешном обновлении и обновленным LessonDto.
     * @throws EntityNotFoundException если урок с указанным идентификатором не найден.
     */
    @Override
    public Response<LessonDto> update(Long id, LessonDto lessonDto) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        existingLesson.setTopic(lessonDto.getTopic());
        existingLesson.setHomework(lessonDto.getHomework());
        existingLesson.setSchedule(lessonMapper.toSchedule(lessonDto.getScheduleId()));
        existingLesson.setCreationDate(lessonDto.getCreationDate());

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return new Response<>("Lesson updated successfully", lessonMapper.toLessonDto(updatedLesson));
    }

    /**
     * Удаление урока по его идентификатору.
     *
     * @param id идентификатор урока, который нужно удалить.
     * @throws EntityNotFoundException если урок с указанным идентификатором не найден.
     */
    @Override
    public void delete(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        lessonRepository.delete(lesson);
        log.info("Deleted lesson with ID: {}", id);
    }
}