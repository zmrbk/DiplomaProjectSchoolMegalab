package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
