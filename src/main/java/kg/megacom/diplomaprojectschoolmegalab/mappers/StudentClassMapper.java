package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Employee;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Mapper для преобразования между сущностью {@link StudentClass} и DTO {@link StudentClassDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link StudentClass} в {@link StudentClassDto}
 * и наоборот, а также для преобразования списков объектов.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class StudentClassMapper {

    private final EmployeeRepository employeeRepository;

    /**
     * Преобразует объект {@link StudentClass} в объект {@link StudentClassDto}.
     *
     * @param studentClass Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link StudentClassDto}.
     */
    public StudentClassDto toStudentClassDto(StudentClass studentClass) {
        StudentClassDto studentClassDto = new StudentClassDto();
        studentClassDto.setId(studentClass.getId());
        studentClassDto.setClassTitle(studentClass.getClassTitle());
        studentClassDto.setEmployeeId(studentClass.getEmployee().getId());
        studentClassDto.setCreationDate(studentClass.getCreationDate());
        return studentClassDto;
    }

    /**
     * Преобразует объект {@link StudentClassDto} в объект {@link StudentClass}.
     *
     * @param studentClassDto DTO, которое нужно преобразовать.
     * @return Преобразованный объект {@link StudentClass}.
     * @throws EntityNotFoundException Если сотрудник не найден по указанному ID.
     */
    public StudentClass toStudentClass(StudentClassDto studentClassDto) {
        StudentClass studentClass = new StudentClass();
        Employee employee = employeeRepository.findById(studentClassDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        studentClass.setClassTitle(studentClassDto.getClassTitle());
        studentClass.setEmployee(employee);
        studentClass.setCreationDate(studentClassDto.getCreationDate());
        return studentClass;
    }

    /**
     * Преобразует список объектов {@link StudentClass} в список объектов {@link StudentClassDto}.
     *
     * @param all Список сущностей, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link StudentClassDto}.
     */
    public List<StudentClassDto> toStudentClassDtoList(List<StudentClass> all) {
        return all.stream()
                .map(this::toStudentClassDto)
                .collect(Collectors.toList());
    }
}
