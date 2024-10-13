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
/**
 * Mapper для преобразования между сущностью {@link Student} и DTO {@link StudentDto}.
 * <p>
 * Этот класс предоставляет методы для конвертации объектов {@link Student} в {@link StudentDto}
 * и наоборот, а также для преобразования списков объектов.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final UserService userService;
    private final StudentClassService studentClassService;
    private final ParentService parentService;

    /**
     * Преобразует объект {@link StudentDto} в объект {@link Student}.
     *
     * @param studentDto DTO, которое нужно преобразовать.
     * @return Преобразованный объект {@link Student}.
     * @throws EntityNotFoundException Если пользователь, класс студента или родитель не найдены по указанным ID.
     */
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

    /**
     * Преобразует объект {@link Student} в объект {@link StudentDto}.
     *
     * @param student Сущность, которую нужно преобразовать.
     * @return Преобразованный объект {@link StudentDto}.
     */
    public StudentDto toStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setBirthday(student.getBirthday());
        studentDto.setClassId(student.getStudentClass().getId());
        studentDto.setUserId(student.getUser().getId());
        studentDto.setParentId(student.getParent().getId());
        studentDto.setParentStatus(student.getParentStatus());
        return studentDto;
    }

    /**
     * Преобразует список объектов {@link Student} в список объектов {@link StudentDto}.
     *
     * @param all Список сущностей, которые нужно преобразовать.
     * @return Список преобразованных объектов {@link StudentDto}.
     */
    public List<StudentDto> toStudentDtoList(List<Student> all) {
        return all.stream()
                .map(this::toStudentDto)
                .collect(Collectors.toList());
    }
}
