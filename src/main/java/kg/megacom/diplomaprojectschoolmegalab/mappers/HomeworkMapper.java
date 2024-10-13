package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.HomeworkDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Homework;
import kg.megacom.diplomaprojectschoolmegalab.entity.Lesson;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.LessonRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

/**
 * Mapper для преобразования между сущностью {@link Homework} и DTO {@link HomeworkDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link HomeworkDto} в {@link Homework}
 * и наоборот, а также для получения списка DTO из списка сущностей.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class HomeworkMapper {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    /**
     * Преобразует объект {@link Homework} в объект {@link HomeworkDto}.
     *
     * @param homework Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link HomeworkDto}.
     */
    public HomeworkDto toDto(Homework homework) {
        HomeworkDto homeworkDto = new HomeworkDto();
        homeworkDto.setId(homework.getId());
        homeworkDto.setIsDone(homework.getIsDone());
        homeworkDto.setTeacherReview(homework.getTeacherReview());
        homeworkDto.setMark(homework.getMark());
        homeworkDto.setCreationDate(homework.getCreationDate());
        homeworkDto.setStudentId(homework.getStudent().getId());
        homeworkDto.setLessonId(homework.getLesson().getId());
        return homeworkDto;
    }

    /**
     * Преобразует объект {@link HomeworkDto} в сущность {@link Homework}.
     *
     * @param homeworkDto DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Homework}.
     * @throws EntityNotFoundException Если ученик или урок с указанными ID не найдены.
     */
    public Homework toEntity(HomeworkDto homeworkDto) {
        Homework homework = new Homework();
        homework.setId(homeworkDto.getId());
        homework.setIsDone(homeworkDto.getIsDone());
        homework.setTeacherReview(homeworkDto.getTeacherReview());
        homework.setMark(homeworkDto.getMark());
        homework.setStudent(toStudent(homeworkDto.getStudentId()));
        homework.setLesson(toLesson(homeworkDto.getLessonId()));
        return homework;
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
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
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
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
    }
}