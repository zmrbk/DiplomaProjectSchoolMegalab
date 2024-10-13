package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;

import java.util.List;

public interface StudentClassService {
    void create(StudentClassDto studentClassDto);
    Response<StudentClassDto> update(StudentClassDto studentClassDto, Long id);
    Response<List<StudentClassDto>> getAll();
    StudentClass getById(Long id);
    void delete(Long id);
}
