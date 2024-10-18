package kg.megacom.diplomaprojectschoolmegalab.repository;

import jakarta.transaction.Transactional;
import kg.megacom.diplomaprojectschoolmegalab.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Modifying
    @Transactional // Important to include this for data modification operations
    @Query("DELETE FROM Attendance a WHERE a.student.id = :studentId")
    void deleteByStudentId(Long studentId);
}
