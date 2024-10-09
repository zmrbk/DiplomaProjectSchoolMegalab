package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Announcement;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
