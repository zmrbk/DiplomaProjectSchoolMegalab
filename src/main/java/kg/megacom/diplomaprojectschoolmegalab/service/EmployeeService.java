package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

public interface EmployeeService {
    void create(EmployeeCreateRequest staffRequest);

    Response findById(Long id);

    Response update(EmployeeCreateRequest staffRequest, Long id);

    Response getAll();

    void delete(Long id);
}
