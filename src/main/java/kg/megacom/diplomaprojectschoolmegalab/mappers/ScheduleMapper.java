package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.ScheduleDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Schedule;
import kg.megacom.diplomaprojectschoolmegalab.entity.Subject;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.SubjectRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentClassRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

/**
 * Mapper для преобразования между сущностью {@link Schedule} и DTO {@link ScheduleDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Schedule} в {@link ScheduleDto}
 * и наоборот, а также для извлечения связанных сущностей.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class ScheduleMapper {

    private final SubjectRepository subjectRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentClassRepository studentClassRepository;

    /**
     * Преобразует объект {@link Schedule} в объект {@link ScheduleDto}.
     *
     * @param schedule Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link ScheduleDto}.
     */
    public ScheduleDto toScheduleDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(schedule.getId());
        scheduleDto.setDayOfWeek(schedule.getDayOfWeek());
        scheduleDto.setQuarter(schedule.getQuarter());
        scheduleDto.setDueTime(schedule.getDueTime());
        scheduleDto.setYear(schedule.getYear());
        scheduleDto.setSubjectId(schedule.getSubject().getId());
        scheduleDto.setTeacherId(schedule.getTeacher().getId());
        scheduleDto.setClassId(schedule.getStudentClass().getId());
        scheduleDto.setIsApprove(schedule.getIsApprove());
        return scheduleDto;
    }

    /**
     * Преобразует объект {@link ScheduleDto} в объект {@link Schedule}.
     *
     * @param scheduleDto DTO, которое нужно преобразовать.
     * @return Преобразованный объект {@link Schedule}.
     */
    public Schedule toSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setDayOfWeek(scheduleDto.getDayOfWeek());
        schedule.setQuarter(scheduleDto.getQuarter());
        schedule.setDueTime(scheduleDto.getDueTime());
        schedule.setYear(scheduleDto.getYear());
        schedule.setSubject(toSubject(scheduleDto.getSubjectId()));
        schedule.setTeacher(toTeacher(scheduleDto.getTeacherId()));
        schedule.setStudentClass(toStudentClass(scheduleDto.getClassId()));
        schedule.setIsApprove(scheduleDto.getIsApprove());
        return schedule;
    }

    /**
     * Извлекает сущность {@link Subject} по ID предмета.
     *
     * @param subjectId ID предмета.
     * @return Сущность {@link Subject}.
     * @throws EntityNotFoundException Если предмет не найден.
     */
    public Subject toSubject(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));
    }

    /**
     * Извлекает сущность {@link Employee} по ID преподавателя.
     *
     * @param teacherId ID преподавателя.
     * @return Сущность {@link Employee}.
     * @throws EntityNotFoundException Если преподаватель не найден.
     */
    public Employee toTeacher(Long teacherId) {
        return employeeRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
    }

    /**
     * Извлекает сущность {@link StudentClass} по ID класса.
     *
     * @param classId ID класса.
     * @return Сущность {@link StudentClass}.
     * @throws EntityNotFoundException Если класс не найден.
     */
    public StudentClass toStudentClass(Long classId) {
        return studentClassRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));
    }
}


