package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.StudentClassMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentClassRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
/**
 * Реализация сервиса для работы с классами студентов.
 *
 * Этот класс предоставляет функциональность для создания, обновления, удаления и получения классов студентов.
 */
@Service
@RequiredArgsConstructor
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;
    private final StudentClassMapper studentClassMapper;

    /**
     * Создание нового класса студента.
     *
     * @param studentClassDto объект класса студента, который нужно создать.
     */
    @Override
    public void create(StudentClassDto studentClassDto) {
        StudentClass studentClass = studentClassMapper.toStudentClass(studentClassDto);
        studentClassRepository.save(studentClass);
    }

    /**
     * Обновление существующего класса студента.
     *
     * @param studentClassDto объект класса студента с обновленной информацией.
     * @param id             идентификатор класса студента, который нужно обновить.
     * @return ответ о результате операции обновления.
     */
    @Override
    public Response<StudentClassDto> update(StudentClassDto studentClassDto, Long id) {
        StudentClass studentClass = studentClassMapper.toStudentClass(studentClassDto);
        studentClass.setId(id);
        return new Response<>("Student class updated successfully", studentClassMapper
                .toStudentClassDto(studentClassRepository.save(studentClass)));
    }

    /**
     * Удаление класса студента.
     *
     * @param id идентификатор класса студента, который нужно удалить.
     */
    @Override
    public void delete(Long id) {
        studentClassRepository.deleteById(id);
    }

    /**
     * Получение всех классов студентов.
     *
     * @return список всех классов студентов.
     */
    @Override
    public Response<List<StudentClassDto>> getAll() {
        List<StudentClass> studentClasses = studentClassRepository.findAll();
        List<StudentClassDto> studentClassDtoList = studentClassMapper.toStudentClassDtoList(studentClasses);
        return new Response<>("All student classes are retrieved successfully", studentClassDtoList);
    }

    /**
     * Получение класса студента по идентификатору.
     *
     * @param id идентификатор класса студента, который нужно получить.
     * @return объект класса студента.
     * @throws EntityNotFoundException если класс студента не найден.
     */
    @Override
    public StudentClass getById(Long id) {
        return studentClassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Student class not found"));
    }

    /**
     * Получение DTO класса студента по идентификатору.
     *
     * @param id идентификатор класса студента, который нужно получить.
     * @return ответ с найденным DTO класса студента.
     */
    public Response<StudentClassDto> getStudentClassDtoById(Long id) {
        StudentClass studentClass = getById(id);
        StudentClassDto studentClassDto = studentClassMapper.toStudentClassDto(studentClass);
        return new Response<>("Student class found", studentClassDto);
    }
}