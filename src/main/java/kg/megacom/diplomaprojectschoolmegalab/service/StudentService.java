package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;

import java.util.List;

public interface StudentService {
    Response<StudentDto> create(StudentDto studentDto);
    Response<StudentDto> findById(Long id);
    Response<List<StudentDto>> getAll();
    Response<StudentDto> update(StudentDto studentDto);
    Response<Void> delete(Long id);
    void deleteMarksByStudentId(Long studentId);
}
