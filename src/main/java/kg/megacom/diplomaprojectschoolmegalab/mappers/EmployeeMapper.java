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
/**
 * Mapper для преобразования между сущностью {@link Employee} и DTO {@link EmployeeDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link EmployeeDto} в {@link Employee}
 * и наоборот, а также для получения списка DTO из списка сущностей.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final UserService userService;

    /**
     * Преобразует объект {@link EmployeeDto} в сущность {@link Employee}.
     *
     * @param employeeCreateRequest DTO, которое нужно преобразовать.
     * @return Преобразованная сущность {@link Employee}.
     * @throws EntityNotFoundException Если пользователь с указанным ID не найден.
     */
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

    /**
     * Преобразует сущность {@link Employee} в объект {@link EmployeeDto}.
     *
     * @param employee Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link EmployeeDto}.
     */
    public EmployeeDto toEmployeeCreateRequest(Employee employee) {
        EmployeeDto employeeCreateRequest = new EmployeeDto();
        employeeCreateRequest.setId(employee.getId());
        employeeCreateRequest.setSalary(employee.getSalary());
        employeeCreateRequest.setPosition(employee.getPosition());
        employeeCreateRequest.setUserId(employee.getUser().getId());
        return employeeCreateRequest;
    }

    /**
     * Преобразует список сущностей {@link Employee} в список DTO {@link EmployeeDto}.
     *
     * @param all Список сущностей, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link EmployeeDto}.
     */
    public List<EmployeeDto> toEmployeeCreateRequestList(List<Employee> all) {
        return all.stream()
                .map(this::toEmployeeCreateRequest)
                .collect(Collectors.toList());
    }
}