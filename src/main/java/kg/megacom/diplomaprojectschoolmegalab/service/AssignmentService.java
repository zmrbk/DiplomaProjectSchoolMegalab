package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.AssignmentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface AssignmentService {
    void create(AssignmentDto assignmentDto);
    Response<AssignmentDto> update(Long id, AssignmentDto assignmentDto);
    Response<List<AssignmentDto>> getAll();
    AssignmentDto getById(Long id);
    void delete(Long id);
}
