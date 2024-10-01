package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.StudentClassDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
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
    public Response update(StudentClassDto studentClassDto, Long id) {
        StudentClass studentClass = studentClassMapper.toStudentClass (studentClassDto);
        studentClass.setId(id);
        return new Response("Grade is updated", studentClassRepository.save(studentClass));
    }

    @Override
    public void delete(Long id) {
        studentClassRepository.deleteById(id);
    }

    @Override
    public Response getAll() {
        List<StudentClass> studentClasses = studentClassRepository.findAll();
        List<StudentClassDto> studentClassDtoList = studentClassMapper.toStudentClassDtoList(studentClasses);
        return new Response("All grades are retrieved", studentClassDtoList);
    }

    @Override
    public Response getById(Long id) {
        StudentClassDto studentClassDto = studentClassMapper.toGradeDto(studentClassRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Grade not found")
        ));
        return new Response("Grade is retrieved", studentClassDto);
    }
}
