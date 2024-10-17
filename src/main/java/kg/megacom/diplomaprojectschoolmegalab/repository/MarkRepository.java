package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByStudentId(Long studentId);

    // Метод для поиска оценок по классу и предмету через уроки
    List<Mark> findByLesson_Schedule_StudentClass_IdAndLesson_Schedule_Subject_Id(Long classId, Long subjectId);



}
