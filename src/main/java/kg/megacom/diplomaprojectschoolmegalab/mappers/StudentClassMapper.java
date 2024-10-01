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

@Component
@RequiredArgsConstructor
public class StudentClassMapper {
    private final EmployeeRepository employeeRepository;
    public StudentClassDto toGradeDto(StudentClass studentClass) {
        StudentClassDto studentClassDto = new StudentClassDto();
        studentClassDto.setId(studentClass.getId());
        studentClassDto.setGradeTitle(studentClass.getGradeTitle());
        studentClassDto.setEmployeeId(studentClass.getEmployee().getId());
        studentClassDto.setCreationDate(studentClass.getCreationDate());
        return studentClassDto;
    }
    public StudentClass toStudentClass (StudentClassDto studentClassDto) {
        StudentClass studentClass = new StudentClass();
        Employee employee = employeeRepository.findById(studentClassDto.getEmployeeId()).orElseThrow(
                () -> new EntityNotFoundException("Employee not found")
        );
        studentClass.setGradeTitle(studentClassDto.getGradeTitle());
        studentClass.setEmployee(employee);
        studentClass.setCreationDate(studentClass.getCreationDate());
        return studentClass;
    }

    public List<StudentClassDto> toStudentClassDtoList(List<StudentClass> all) {
        return all.stream()
                .map(this::toGradeDto)
                .collect(Collectors.toList());

    }
}
