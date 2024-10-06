package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

import java.util.List;

public interface EmployeeService {
    void create(EmployeeDto staffRequest);
    Response<EmployeeDto> findById(Long id);
    Response<EmployeeDto> update(EmployeeDto staffRequest, Long id);
    Response<List<EmployeeDto>> getAll();
    void delete(Long id);
}
