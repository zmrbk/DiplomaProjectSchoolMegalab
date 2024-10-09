package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.entity.User;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.service.UserService;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import kg.megacom.diplomaprojectschoolmegalab.service.ParentService;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final UserService userService;
    private final StudentClassService studentClassService;
    private final ParentService parentService;

    public Student toStudent(StudentDto studentDto) {
        Student student = new Student();
        User user = userService.getById(studentDto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("User with ID " + studentDto.getUserId() + " not found")
        );
        student.setUser(user);
        student.setBirthday(studentDto.getBirthday());
        StudentClass studentClass = studentClassService.getById(studentDto.getClassId());
        student.setStudentClass(studentClass);
        Parent parent = parentService.getById(studentDto.getParentId());
        student.setParent(parent);
        student.setParentStatus(studentDto.getParentStatus());
        return student;
    }

    public StudentDto toStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setBirthday(student.getBirthday());
        studentDto.setClassId(student.getStudentClass().getId());
        studentDto.setUserId(student.getUser().getId());
        studentDto.setParentId(student.getParent().getId()); // This gets the ID from the Parent entity
        studentDto.setParentStatus(student.getParentStatus());
        return studentDto;
    }

    public List<StudentDto> toStudentDtoList(List<Student> all) {
        return all.stream()
                .map(this::toStudentDto)
                .collect(Collectors.toList());
    }
}
