package kg.megacom.diplomaprojectschoolmegalab.service.impl;


import kg.megacom.diplomaprojectschoolmegalab.dto.EmployeeDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.EmployeeMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.EmployeeService;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса для управления сотрудниками.
 *
 * Этот класс предоставляет функциональность для создания, обновления, поиска и удаления
 * записей о сотрудниках.
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserService userService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Создание нового сотрудника.
     *
     * @param request объект, содержащий информацию о новом сотруднике.
     * @throws EntityNotFoundException если пользователь, связанный с сотрудником, не найден.
     */
    @Override
    public void create(EmployeeDto request) {
        if (userService.getById(request.getUserId()).isEmpty()) {
            throw new EntityNotFoundException("Employee not found, please create new employee first");
        }
        Employee employee = employeeMapper.toEmployee(request);
        employeeRepository.save(employee);
    }

    /**
     * Поиск сотрудника по идентификатору.
     *
     * @param id идентификатор сотрудника.
     * @return объект Response с информацией о найденном сотруднике.
     * @throws EntityNotFoundException если сотрудник не найден.
     */
    @Override
    public Response<EmployeeDto> findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        EmployeeDto employeeCreateRequest = employeeMapper.toEmployeeCreateRequest(employee);
        return new Response<>("Find Employee by id: ", employeeCreateRequest);
    }

    /**
     * Обновление информации о сотруднике.
     *
     * @param request объект, содержащий обновленную информацию о сотруднике.
     * @param id идентификатор сотрудника для обновления.
     * @return объект Response с информацией о обновленном сотруднике.
     * @throws EntityNotFoundException если пользователь не найден.
     */
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

    /**
     * Получение списка всех сотрудников.
     *
     * @return объект Response со списком всех сотрудников.
     */
    @Override
    public Response<List<EmployeeDto>> getAll() {
        return new Response<>("Get all employees: ", employeeMapper.toEmployeeCreateRequestList(employeeRepository.findAll()));
    }

    /**
     * Удаление сотрудника по идентификатору.
     *
     * @param id идентификатор сотрудника для удаления.
     */
    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}