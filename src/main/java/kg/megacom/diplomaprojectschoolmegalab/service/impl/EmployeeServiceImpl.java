package kg.megacom.diplomaprojectschoolmegalab.service.impl;


import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.EmployeeMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.EmployeeService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserService userService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public void createEmployee(EmployeeCreateRequest request) {
        if (userService.getById(request.getUserId()).isEmpty()){
            throw new EntityNotFoundException("User not found, please create new user first");
        }
        Employee employee = employeeMapper.toEmployee(request, new Employee());
        employeeRepository.save(employee);
    }

    @Override
    public Response findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Employee not found"));
        EmployeeCreateRequest employeeCreateRequest = employeeMapper.toEmployeeCreateRequest(employee);
        return new Response("Find Employee by id", employeeCreateRequest);

    }

    @Override
    public Response updateEmployee(EmployeeCreateRequest request, Long id) {
        if (userService.getById(request.getUserId()).isEmpty()){
            return new Response("Error: user_id " + request.getUserId() + " is not found", "Check user_id");
        }
        Employee oldEmployee = employeeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Staff not found"));
        Employee newEmployee = employeeMapper.toEmployee(request, oldEmployee);
        newEmployee.setId(id);
        employeeRepository.save(newEmployee);
        return new Response("Update Employee", employeeMapper.toEmployeeCreateRequest(newEmployee));
    }

    @Override
    public Response getAllEmployees() {
        return new Response("Get all Employee", employeeMapper.toEmployeeCreateRequestList(employeeRepository.findAll()));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

}
