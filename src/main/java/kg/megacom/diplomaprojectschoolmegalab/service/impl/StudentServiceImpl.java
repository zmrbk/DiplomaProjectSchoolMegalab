package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.StudentDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Student;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.StudentMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.StudentRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.StudentService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Response<StudentDto> create(StudentDto studentDto) {
        Student student = studentMapper.toStudent(studentDto);
        log.info("Creating student: {}", student);
        Student savedStudent = studentRepository.save(student);
        StudentDto savedStudentDto = studentMapper.toStudentDto(savedStudent);
        return new Response<>("Student created successfully", savedStudentDto);
    }

    @Override
    public Response<StudentDto> findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + id + " not found"));
        StudentDto studentDto = studentMapper.toStudentDto(student);
        return new Response<>("Student found", studentDto);
    }

    @Override
    public Response<List<StudentDto>> getAll() {
        List<Student> students = studentRepository.findAll();
        log.info("Get all students: {}", students);
        List<StudentDto> studentDtos = studentMapper.toStudentDtoList(students);
        return new Response<>("All students", studentDtos);
    }

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

    @Override
    public Response<Void> delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student with ID " + id + " not found");
        }
        studentRepository.deleteById(id);
        return new Response<>("Student deleted successfully", null);
    }
}
