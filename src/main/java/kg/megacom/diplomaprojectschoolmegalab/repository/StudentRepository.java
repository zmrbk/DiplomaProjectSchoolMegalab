package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
