package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.ParentDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.entity.Parent;
import kg.megacom.diplomaprojectschoolmegalab.entity.StudentClass;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.StudentClassMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentClassRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentClassServiceImpl implements StudentClassService {

    private final StudentClassRepository studentClassRepository;
    private final StudentClassMapper studentClassMapper;

    @Override
    public void create(StudentClassDto studentClassDto) {
        StudentClass studentClass = studentClassMapper.toStudentClass (studentClassDto);
        studentClassRepository.save(studentClass);
    }

    @Override
    public Response<StudentClassDto> update(StudentClassDto studentClassDto, Long id) {
        StudentClass studentClass = studentClassMapper.toStudentClass(studentClassDto);
        studentClass.setId(id);
        return new Response<>("Student class is updated", studentClassMapper
                .toStudentClassDto(studentClassRepository.save(studentClass)));
    }

    @Override
    public void delete(Long id) {
        studentClassRepository.deleteById(id);
    }

    @Override
    public Response<List<StudentClassDto>> getAll() {
        List<StudentClass> studentClasses = studentClassRepository.findAll();
        List<StudentClassDto> studentClassDtoList = studentClassMapper.toStudentClassDtoList(studentClasses);
        return new Response<>("All student classes are retrieved", studentClassDtoList);
    }

    @Override
    public StudentClass getById(Long id) {
        return studentClassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Student class not found"));
    }

    public Response<StudentClassDto> getStudentClassDtoById(Long id) {
        StudentClass studentClass = getById(id);  // Fetch the entity
        StudentClassDto studentClassDto = studentClassMapper.toStudentClassDto(studentClass);  // Convert to DTO
        return new Response<>("Student class found", studentClassDto);
    }
}
