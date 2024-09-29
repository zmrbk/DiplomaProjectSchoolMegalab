package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeCreateRequest;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final UserService userService;
    public Employee toEmployee(EmployeeCreateRequest employeeCreateRequest, Employee employee) {
        User user = userService.getById(employeeCreateRequest.getUserId()).orElseThrow();
        employee.setId(employeeCreateRequest.getId());
        employee.setSalary(employeeCreateRequest.getSalary());
        employee.setPosition(employeeCreateRequest.getPosition());
        employee.setUser(user);
        return employee;
    }

    public EmployeeCreateRequest toStaffCreateRequest(Employee employee) {
        EmployeeCreateRequest employeeCreateRequest = new EmployeeCreateRequest();
        employeeCreateRequest.setId(employee.getId());
        employeeCreateRequest.setSalary(employee.getSalary());
        employeeCreateRequest.setPosition(employee.getPosition());
        employeeCreateRequest.setUserId(employee.getUser().getId());
        return employeeCreateRequest;
    }

    public List<EmployeeCreateRequest> toStaffCreateRequestList(List<Employee> all) {
        return all.stream()
                .map(this::toStaffCreateRequest)
                .collect(Collectors.toList());

    }
}
