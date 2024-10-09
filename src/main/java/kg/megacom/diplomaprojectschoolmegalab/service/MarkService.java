package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.MarkDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface MarkService {
    void create(MarkDto markDto);
    Response<List<MarkDto>> getAll();
    MarkDto getById(Long id);
    Response<MarkDto> update(Long id, MarkDto markDto);
    void delete(Long id);
    void deleteMarksByStudentId(Long studentId);
}
