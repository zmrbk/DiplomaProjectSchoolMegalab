package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final UserService userService;

    public Employee toEmployee(EmployeeDto employeeCreateRequest) {
        Employee employee = new Employee();
        User user = userService.getById(employeeCreateRequest.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("User with id " + employeeCreateRequest.getUserId() + " not found")
        );
        employee.setSalary(employeeCreateRequest.getSalary());
        employee.setPosition(employeeCreateRequest.getPosition());
        employee.setUser(user);
        return employee;
    }

    public EmployeeDto toEmployeeCreateRequest(Employee employee) {
        EmployeeDto employeeCreateRequest = new EmployeeDto();
        employeeCreateRequest.setId(employee.getId());
        employeeCreateRequest.setSalary(employee.getSalary());
        employeeCreateRequest.setPosition(employee.getPosition());
        employeeCreateRequest.setUserId(employee.getUser().getId());
        return employeeCreateRequest;
    }

    public List<EmployeeDto> toEmployeeCreateRequestList(List<Employee> all) {
        return all.stream()
                .map(this::toEmployeeCreateRequest)
                .collect(Collectors.toList());
    }
}
