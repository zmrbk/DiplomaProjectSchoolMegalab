package kg.megacom.diplomaprojectschoolmegalab.service;

import kg.megacom.diplomaprojectschoolmegalab.dto.Response;
import kg.megacom.diplomaprojectschoolmegalab.dto.SubjectsDto;
import kg.megacom.diplomaprojectschoolmegalab.dto.TopicDto;
import kg.megacom.diplomaprojectschoolmegalab.exceptions.EntityNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface SubjectService {
    SubjectsDto create(SubjectsDto subjectsDto) throws AccountNotFoundException;
    Response<SubjectsDto> update(SubjectsDto subjectsDto, Long id) throws AccountNotFoundException;
    Response<String> delete(Long id)throws EntityNotFoundException;
    Response<List<SubjectsDto>> getAll();
    Response<SubjectsDto> getById(Long id);
    List<SubjectsDto> getSubjectsByStudentId(Long studentId);
    List<TopicDto> getTopicsBySubjectId(Long subjectId);
}
