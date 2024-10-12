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

@Component
@RequiredArgsConstructor
public class AttendanceMapper {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    // Convert Attendance entity to AttendanceDto
    public AttendanceDto toDto(Attendance attendance) {
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setId(attendance.getId());
        attendanceDto.setAttended(attendance.getAttended());
        attendanceDto.setLessonId(attendance.getLesson().getId());
        attendanceDto.setStudentId(attendance.getStudent().getId());
        return attendanceDto;
    }

    // Convert AttendanceDto to Attendance entity
    public Attendance toEntity(AttendanceDto attendanceDto) {
        Attendance attendance = new Attendance();
        attendance.setId(attendanceDto.getId());
        attendance.setAttended(attendanceDto.getAttended());
        attendance.setLesson(toLesson(attendanceDto.getLessonId()));
        attendance.setStudent(toStudent(attendanceDto.getStudentId()));
        return attendance;
    }

    // Retrieve Lesson entity based on lessonId
    public Lesson toLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
    }

    // Retrieve Student entity based on studentId
    public Student toStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
    }
}
