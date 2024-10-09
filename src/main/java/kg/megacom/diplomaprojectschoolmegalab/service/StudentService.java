package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;

import java.util.List;

public interface StudentService {
    Response<StudentDto> create(StudentDto studentDto);
    Response<StudentDto> findById(Long id); // New method
    Response<List<StudentDto>> getAll(); // New method
    Response<StudentDto> update(StudentDto studentDto); // New method
    Response<Void> delete(Long id); // New method
}
