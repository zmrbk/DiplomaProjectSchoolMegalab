package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.StudentMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.MarkService;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Реализация сервиса для работы со студентами.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения студентов.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final MarkService markService;

    /**
     * Создание нового студента.
     *
     * @param studentDto объект студента, который нужно создать.
     * @return ответ с информацией о созданном студенте.
     */
    @Override
    public Response<StudentDto> create(StudentDto studentDto) {
        Student student = studentMapper.toStudent(studentDto);
        log.info("Creating student: {}", student);
        Student savedStudent = studentRepository.save(student);
        StudentDto savedStudentDto = studentMapper.toStudentDto(savedStudent);
        return new Response<>("Student created successfully", savedStudentDto);
    }

    /**
     * Получение студента по идентификатору.
     *
     * @param id идентификатор студента, который нужно получить.
     * @return ответ с найденным студентом.
     * @throws EntityNotFoundException если студент не найден.
     */
    @Override
    public Response<StudentDto> findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found"));
        StudentDto studentDto = studentMapper.toStudentDto(student);
        return new Response<>("Student found", studentDto);
    }

    /**
     * Получение всех студентов.
     *
     * @return ответ со списком всех студентов.
     */
    @Override
    public Response<List<StudentDto>> getAll() {
        List<Student> students = studentRepository.findAll();
        log.info("Get all students: {}", students);
        List<StudentDto> studentDtos = studentMapper.toStudentDtoList(students);
        return new Response<>("All students", studentDtos);
    }

    /**
     * Обновление существующего студента.
     *
     * @param studentDto объект студента с обновленной информацией.
     * @return ответ с информацией о обновленном студенте.
     * @throws EntityNotFoundException если студент не найден.
     */
    @Override
    public Response<StudentDto> update(StudentDto studentDto) {
        Student existingStudent = studentRepository.findById(studentDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentDto.getId() + " not found"));
        existingStudent.setBirthday(studentDto.getBirthday());
        existingStudent.setParentStatus(studentDto.getParentStatus());
        Student updatedStudent = studentRepository.save(existingStudent);
        StudentDto updatedStudentDto = studentMapper.toStudentDto(updatedStudent);
        return new Response<>("Student updated successfully", updatedStudentDto);
    }

    /**
     * Удаление студента.
     *
     * @param id идентификатор студента, который нужно удалить.
     * @return ответ о результате операции удаления.
     * @throws EntityNotFoundException если студент не найден.
     */
    @Override
    public Response<Void> delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student with ID " + id + " not found");
        }
        deleteMarksByStudentId(id);
        studentRepository.deleteById(id);
        return new Response<>("Student deleted successfully", null);
    }

    /**
     * Удаление оценок студента по его идентификатору.
     *
     * @param studentId идентификатор студента, оценки которого нужно удалить.
     */
    @Override
    public void deleteMarksByStudentId(Long studentId) {
        markService.deleteMarksByStudentId(studentId);
    }
}