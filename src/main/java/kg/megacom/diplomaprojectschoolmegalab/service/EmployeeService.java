package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;

public interface EmployeeService {
    void createEmployee(EmployeeCreateRequest staffRequest);

    Response findEmployeeById(Long id);

    Response updateEmployee(EmployeeCreateRequest staffRequest, Long id);

    Response getAllEmployees();

    void deleteEmployee(Long id);
}
