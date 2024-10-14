package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Lesson;
import kg.megacom.diplomaprojectschoolmegalab.entity.Mark;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.LessonRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Mapper для преобразования между сущностью {@link Mark} и DTO {@link MarkDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link MarkDto} в {@link Mark}
 * и наоборот.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class MarkMapper {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    /**
     * Преобразует объект {@link Mark} в объект {@link MarkDto}.
     *
     * @param mark Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link MarkDto}.
     */
    public MarkDto toMarkDto(Mark mark) {
        MarkDto markDto = new MarkDto();
        markDto.setId(mark.getId());
        markDto.setMark(mark.getMark());
        markDto.setStudentId(mark.getStudent().getId());
        markDto.setLessonId(mark.getLesson().getId());
        return markDto;
    }

    /**
     * Преобразует объект {@link MarkDto} в сущность {@link Mark}.
     *
     * @param markDto DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Mark}.
     * @throws EntityNotFoundException Если студент или урок с указанным ID не найдены.
     */
    public Mark toMark(MarkDto markDto) {
        Mark mark = new Mark();
        mark.setMark(markDto.getMark());
        mark.setStudent(toStudent(markDto.getStudentId()));
        mark.setLesson(toLesson(markDto.getLessonId()));
        return mark;
    }

    /**
     * Получает сущность {@link Student} по ID студента.
     *
     * @param studentId ID студента.
     * @return Сущность {@link Student}.
     * @throws EntityNotFoundException Если студент с указанным ID не найден.
     */
    public Student toStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    /**
     * Получает сущность {@link Lesson} по ID урока.
     *
     * @param lessonId ID урока.
     * @return Сущность {@link Lesson}.
     * @throws EntityNotFoundException Если урок с указанным ID не найден.
     */
    public Lesson toLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
    }
}