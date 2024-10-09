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

@Component
@RequiredArgsConstructor
public class MarkMapper {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    public MarkDto toMarkDto(Mark mark) {
        MarkDto markDto = new MarkDto();
        markDto.setId(mark.getId());
        markDto.setMark(mark.getMark());
        markDto.setStudentId(mark.getStudent().getId());
        markDto.setLessonId(mark.getLesson().getId());
        return markDto;
    }

    public Mark toMark(MarkDto markDto) {
        Mark mark = new Mark();
        mark.setMark(markDto.getMark());
        mark.setStudent(toStudent(markDto.getStudentId()));
        mark.setLesson(toLesson(markDto.getLessonId()));
        return mark;
    }

    public Student toStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public Lesson toLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
    }
}
