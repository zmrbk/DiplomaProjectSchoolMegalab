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

@Component
@RequiredArgsConstructor
public class HomeworkMapper {

    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

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

    public Lesson toLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
    }

    public Student toStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
    }
}
