package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Role;

import java.util.List;
import java.util.Set;

public interface AssignmentService {
    void create(AssignmentDto assignmentDto);
    Response<AssignmentDto> update(Long id, AssignmentDto assignmentDto);
    Response<List<AssignmentDto>> getAll();
    AssignmentDto getById(Long id);
    void delete(Long id);

    AssignmentDto markAsCompleted(Long id);
    List<AssignmentDto> getAssignmentsByAuthorRole(Set<Role> roleName);
    AssignmentDto createAssignmentForCaptain(AssignmentDto assignmentDto);
    List<AssignmentDto> getCompletedAssignmentsByCaptain(Long captainId);
    List<AssignmentDto> getAssignmentsByCaptainRole();

    void completeAssignment(Long assignmentId);

}
