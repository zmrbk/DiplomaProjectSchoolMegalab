package kg.megacom.diplomaprojectschoolmegalab.service.impl;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Subject;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityAlreadyExistsException;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;
import kg.megacom.diplomaprojectschoolmegalab.mappers.SubjectMapper;
import kg.megacom.diplomaprojectschoolmegalab.repository.SubjectRepository;
import kg.megacom.diplomaprojectschoolmegalab.service.SubjectService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public void create(SubjectsDto subjectsDto) throws EntityNotFoundException {
        if (subjectRepository.existsByTitle(subjectsDto.getTitle())) {
            throw new EntityAlreadyExistsException("A subject with this title already exists");
        }
        Subject subject = subjectMapper.toSubject(subjectsDto);
        log.info("Create subject with username: {}", subjectsDto.getTitle());
        subjectRepository.save(subject);
    }

    @Override
    public Response<SubjectsDto> update(SubjectsDto subjectsDto, Long id) throws EntityNotFoundException {
        if (subjectRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Subject not found");
        }
        Subject subject = subjectMapper.toSubject(subjectsDto);
        subject.setId(id);
        subjectRepository.save(subject);
        return new Response<>("Subject updated", subjectMapper.toSubjectDto(subject));
    }

    @Override
    public Response<String> delete(Long id) throws EntityNotFoundException {

        if (subjectRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Subject not found");
        }
        subjectRepository.deleteById(id);
        return new Response<>("Subject: " + id,  "Deleted successfully");
    }

    @Override
    public Response<List<SubjectsDto>> getAll() {
        List<SubjectsDto> list = subjectMapper.toSubjectsDtoList(subjectRepository.findAll());
        return new Response<>("All Subjects", list);
    }

    @Override
    public Response<SubjectsDto> getById(Long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Subject not found"));
        return new Response<>("Get Subject ID", subjectMapper.toSubjectDto(subject));
    }
}
