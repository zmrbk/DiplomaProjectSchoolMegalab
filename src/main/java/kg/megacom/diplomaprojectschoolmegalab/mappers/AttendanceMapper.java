package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.AttendanceDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Attendance;
import kg.megacom.diplomaprojectschoolmegalab.entity.Lesson;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.LessonRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

/**
 * Mapper для преобразования между сущностью {@link Attendance} и DTO {@link AttendanceDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Attendance} в {@link AttendanceDto}
 * и наоборот, а также для получения уроков и студентов из репозиториев.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AttendanceMapper {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    /**
     * Преобразует сущность {@link Attendance} в {@link AttendanceDto}.
     *
     * @param attendance Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link AttendanceDto}.
     */
    public AttendanceDto toDto(Attendance attendance) {
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setId(attendance.getId());
        attendanceDto.setAttended(attendance.getAttended());
        attendanceDto.setLessonId(attendance.getLesson().getId());
        attendanceDto.setStudentId(attendance.getStudent().getId());
        return attendanceDto;
    }

    /**
     * Преобразует объект {@link AttendanceDto} в сущность {@link Attendance}.
     *
     * @param attendanceDto DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Attendance}.
     */
    public Attendance toEntity(AttendanceDto attendanceDto) {
        Attendance attendance = new Attendance();
        attendance.setId(attendanceDto.getId());
        attendance.setAttended(attendanceDto.getAttended());
        attendance.setLesson(toLesson(attendanceDto.getLessonId()));
        attendance.setStudent(toStudent(attendanceDto.getStudentId()));
        return attendance;
    }

    /**
     * Получает сущность {@link Lesson} по идентификатору урока.
     *
     * @param lessonId Идентификатор урока.
     * @return Сущность {@link Lesson} урока.
     * @throws EntityNotFoundException Если урок не найден.
     */
    public Lesson toLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
    }

    /**
     * Получает сущность {@link Student} по идентификатору студента.
     *
     * @param studentId Идентификатор студента.
     * @return Сущность {@link Student} студента.
     * @throws EntityNotFoundException Если студент не найден.
     */
    public Student toStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
    }
}
