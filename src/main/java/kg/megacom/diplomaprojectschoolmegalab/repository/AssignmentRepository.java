package kg.megacom.diplomaprojectschoolmegalab.repository;

import kg.megacom.diplomaprojectschoolmegalab.entity.Assignment;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // Метод для поиска поручений по ролям автора
    @Query("SELECT a FROM Assignment a JOIN a.author.roles role WHERE role IN :roles")
    List<Assignment> findByAuthor_Roles(@Param("roles") Set<Role> roles);

    List<Assignment> findByReceiverIdAndIsDoneTrue(Long receiverId);

    // Метод для поиска поручений, назначенных пользователям с ролью CAPTAIN
    @Query("SELECT a FROM Assignment a JOIN a.receiver r JOIN r.roles role WHERE role.roleName = :roleName")
    List<Assignment> findByReceiverRole(@Param("roleName") String roleName);
}
