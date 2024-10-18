package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import jakarta.transaction.Transactional;
import kg.megacom.diplomaprojectschoolmegalab.dto.CharterDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Charter;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.CharterMapper;
import kg.megacom.diplomaprojectschoolmegalab.mappers.StudentMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.*;
import kg.megacom.diplomaprojectschoolmegalab.service.AttendanceService;
import kg.megacom.diplomaprojectschoolmegalab.service.MarkService;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

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
    private final CharterRepository charterRepository;
    private final CharterMapper charterMapper;
    private final AttendanceService attendanceService;
    private final ReviewRepository reviewRepository;
    private final HomeworkRepository homeworkRepository;

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
        return new Response<>("All students retrieved successfully", studentDtos);
    }

    /**
     * Обновление существующего студента.
     *
     * @param studentDto объект студента с обновленной информацией.
     * @return ответ с информацией о обновленном студенте.
     * @throws EntityNotFoundException если студент не найден.
     */
    @Override
    public Response<StudentDto> update(StudentDto studentDto, Long id) {
        Student existingStudent = studentRepository.findById(id)
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

    @Override
    @Transactional
    public StudentDto appointClassCaptain(Long id) {
        // Находим студента по ID
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Получаем всех студентов из этого же класса
        List<Student> classmates = studentRepository.findByStudentClassId(student.getStudentClass().getId());

        // Сбрасываем статус старосты у всех одноклассников
        classmates.forEach(classmate -> {
            classmate.setIsClassCaptain(false);
            studentRepository.save(classmate);
        });

        // Назначаем текущего студента старостой
        student.setIsClassCaptain(true);
        studentRepository.save(student);

        // Возвращаем DTO назначенного старосты
        return studentMapper.toStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudentsInClass(Long classId) {
        // Получаем всех студентов в классе по classId
        List<Student> students = studentRepository.findByStudentClassId(classId);

        // Преобразуем список студентов в DTO и возвращаем
        return students.stream()
                .map(studentMapper::toStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CharterDto createAutobiography(Long studentId, CharterDto charterDto) {
        // Находим студента по ID
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Создаем сущность Charter (автобиография) на основе DTO
        Charter charter = new Charter();
        charter.setTitle("Автобиография студента: " + student.getUser().getFirstName() + " " + student.getUser().getLastName());
        charter.setDescription(charterDto.getDescription());
        charter.setCreationDate(charterDto.getCreationDate());
        charter.setEmployee(charterRepository.findById(charterDto.getEmployeeId()).get().getEmployee());

        // Сохраняем созданную автобиографию
        charter = charterRepository.save(charter);

        // Возвращаем DTO созданной автобиографии
        return charterMapper.toCharterDto(charter);
    }

    @Override
    public Response<StudentDto> registerChild(StudentDto studentDto) {
        log.info("Registering child: {}", studentDto);

        // Add specific logic for registering a child (e.g., associating with a parent)
        if (studentDto.getParentStatus() == null) {
            throw new IllegalArgumentException("Parent status must be provided for child registration");
        }

        // Use the existing create method to handle saving the student entity
        return create(studentDto);
    }

    @Override
    @Transactional
    public Response<Void> expel(Long studentId) {
        // Check if the student exists
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Student with ID " + studentId + " not found");
        }

        // Delete homeworks associated with the student
        homeworkRepository.deleteByStudentId(studentId);  // Ensure this method exists in the repository

        // Delete reviews associated with the student
        reviewRepository.deleteByStudentId(studentId);

        // Delete related attendance records
        attendanceService.deleteAttendancesByStudentId(studentId);

        // Optionally delete other associated data like marks
        deleteMarksByStudentId(studentId);

        // Delete the student
        studentRepository.deleteById(studentId);

        log.info("Student with ID {} has been expelled successfully.", studentId);
        return new Response<>("Student expelled successfully", null);
    }

}