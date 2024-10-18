package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findByLesson_Schedule_StudentClass_IdAndLesson_Schedule_Subject_IdAndIsDoneTrue(Long classId, Long subjectId);
    List<Homework> findByLesson_Id(Long lessonId);
    List<Homework> findByLesson_Schedule_Subject_Id(Long subjectId);
    void deleteByStudentId(Long studentId);
}
