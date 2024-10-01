package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

public interface StudentClassService {
    void create(StudentClassDto grade);
    Response update(StudentClassDto studentClassDto, Long id);
    void delete(Long id) ;
    Response getAll();
    Response getById(Long id);
}
