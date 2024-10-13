package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.LessonDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Lesson;
import kg.megacom.diplomaprojectschoolmegalab.entity.Schedule;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.ScheduleRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
/**
 * Mapper для преобразования между сущностью {@link Lesson} и DTO {@link LessonDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link LessonDto} в {@link Lesson}
 * и наоборот.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class LessonMapper {

    private final ScheduleRepository scheduleRepository;

    /**
     * Преобразует объект {@link Lesson} в объект {@link LessonDto}.
     *
     * @param lesson Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link LessonDto}.
     */
    public LessonDto toLessonDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setTopic(lesson.getTopic());
        lessonDto.setHomework(lesson.getHomework());
        lessonDto.setScheduleId(lesson.getSchedule().getId());
        lessonDto.setCreationDate(lesson.getCreationDate());
        return lessonDto;
    }

    /**
     * Преобразует объект {@link LessonDto} в сущность {@link Lesson}.
     *
     * @param lessonDto DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Lesson}.
     * @throws EntityNotFoundException Если расписание с указанным ID не найдено.
     */
    public Lesson toLesson(LessonDto lessonDto) {
        Lesson lesson = new Lesson();
        lesson.setTopic(lessonDto.getTopic());
        lesson.setHomework(lessonDto.getHomework());
        lesson.setSchedule(toSchedule(lessonDto.getScheduleId()));
        lesson.setCreationDate(lessonDto.getCreationDate());
        return lesson;
    }

    /**
     * Получает сущность {@link Schedule} по ID расписания.
     *
     * @param scheduleId ID расписания.
     * @return Сущность {@link Schedule}.
     * @throws EntityNotFoundException Если расписание с указанным ID не найдено.
     */
    public Schedule toSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + scheduleId));
    }
}