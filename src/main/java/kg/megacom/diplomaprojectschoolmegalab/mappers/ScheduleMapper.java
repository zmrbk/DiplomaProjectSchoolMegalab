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

@Component
@RequiredArgsConstructor
public class ScheduleMapper {

    private final SubjectRepository subjectRepository;
    private final EmployeeRepository employeeRepository;
    private final StudentClassRepository studentClassRepository;

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

    public Subject toSubject(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with ID: " + subjectId));
    }

    public Employee toTeacher(Long teacherId) {
        return employeeRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with ID: " + teacherId));
    }

    public StudentClass toStudentClass(Long classId) {
        return studentClassRepository.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with ID: " + classId));
    }
}

