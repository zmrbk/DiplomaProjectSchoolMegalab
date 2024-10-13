package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.EmployeeMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.EmployeeService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserService userService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public void create(EmployeeDto request) {
        if (userService.getById(request.getUserId()).isEmpty()) {
            throw new EntityNotFoundException("Employee not found, please create new employee first");
        }
        Employee employee = employeeMapper.toEmployee(request);
        employeeRepository.save(employee);
    }

    @Override
    public Response<EmployeeDto> findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        EmployeeDto employeeCreateRequest = employeeMapper.toEmployeeCreateRequest(employee);
        return new Response<>("Find Employee by id: ", employeeCreateRequest);
    }

    @Override
    public Response<EmployeeDto> update(EmployeeDto request, Long id) {
        if (userService.getById(request.getUserId()).isEmpty()) {
            return new Response<>("Error: user_id " + request.getUserId() + " is not found", null);
        }
        Employee newEmployee = employeeMapper.toEmployee(request);
        newEmployee.setId(id);
        employeeRepository.save(newEmployee);
        return new Response<>("Update Employee: ", employeeMapper.toEmployeeCreateRequest(newEmployee));
    }

    @Override
    public Response<List<EmployeeDto>> getAll() {
        return new Response<>("Get all employees: ", employeeMapper.toEmployeeCreateRequestList(employeeRepository.findAll()));
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
