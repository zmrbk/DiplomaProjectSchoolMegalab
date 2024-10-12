package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
