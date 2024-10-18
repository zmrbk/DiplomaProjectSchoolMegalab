package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Review;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);
    List<Review> findByAuthorId(Long authorId);

}
