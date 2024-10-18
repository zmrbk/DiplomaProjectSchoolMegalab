package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByTitle(String title);
    @Query("SELECT s FROM Subject s JOIN s.students st WHERE st.id = :studentId")
    List<Subject> findSubjectsByStudentId(@Param("studentId") Long studentId);
}
