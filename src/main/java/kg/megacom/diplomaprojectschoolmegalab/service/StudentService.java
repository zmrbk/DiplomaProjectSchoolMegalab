package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;

import java.util.List;

public interface StudentService {
    Response<StudentDto> create(StudentDto studentDto);
    Response<StudentDto> findById(Long id);
    Response<List<StudentDto>> getAll();
    Response<StudentDto> update(StudentDto studentDto, Long id);
    Response<Void> delete(Long id);
    void deleteMarksByStudentId(Long studentId);
    StudentDto appointClassCaptain(Long id);
    List<StudentDto> getAllStudentsInClass(Long classId);
    CharterDto createAutobiography(Long studentId, CharterDto charterDto);
    Response<StudentDto> registerChild(StudentDto studentDto);
    Response<Void> expel(Long studentId);
}
